package com.jpacourse.persistance.dao;

import com.jpacourse.persistence.dao.PatientRepository;
import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import org.hibernate.OptimisticLockException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;
import static org.junit.Assert.assertThrows;

@SpringBootTest
@Transactional
public class PatientRepositoryTest {



    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private EntityManager entityManager2;
    @Test
    public void testFindPatientsByLastName() {
        // when
        List<PatientEntity> patients = patientRepository.findPatientsByLastName("Smith");

        // then
        assertThat(patients).isNotEmpty();
        assertThat(patients.get(0).getLastName()).isEqualTo("Smith");
    }
    @Test
    public void testFindPatientsWithMoreThanXVisits() {
        // given
        Long visitCount = 1L;

        // when
        List<PatientEntity> patients = patientRepository.findPatientsWithMoreThanXVisits(visitCount);

        // then
        assertThat(patients).isNotEmpty();
        assertThat(patients.size()).isGreaterThan(0);

        // dodatkowe sprawdzenie
        patients.forEach(patient -> {
            assertThat(patient.getVisits().size()).isGreaterThan(visitCount.intValue()); // Konwersja na int
        });
    }
    @Test
    public void testFindPatientsByRegistrationDateAfter() {
        // given
        LocalDate date = LocalDate.of(2023, 1, 1);

        // when
        List<PatientEntity> patients = patientRepository.findPatientsByRegistrationDateAfter(date);

        // then
        assertThat(patients).isNotEmpty();
        assertThat(patients.size()).isGreaterThan(0);

        // dodatkowe sprawdzenie
        patients.forEach(patient -> {
            assertThat(patient.getRegistrationDate()).isAfter(date);
        });
    }


    @Test
    @Transactional
    public void testOptimisticLocking() {
        // Tworzenie danych
        AddressEntity address = new AddressEntity();
        address.setAddressLine1("123 Main St");
        address.setCity("New York");
        address.setPostalCode("10001");
        entityManager.persist(address);

        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Alice");
        patient.setLastName("Smith");
        patient.setPatientNumber("P022");
        patient.setTelephoneNumber("555-9876");
        patient.setDateOfBirth(LocalDate.of(1990, 3, 25));
        patient.setRegistrationDate(LocalDate.of(2023, 1, 15));
        patient.setAddress(address);
        patient.setVisits(new ArrayList<>()); // Inicjalizacja kolekcji
        patient.setVersion(0);
        entityManager.persist(patient);
        entityManager.flush();

        // Pobranie encji w dwóch transakcjach
        PatientEntity patient1 = patientRepository.findById(patient.getId()).get();

       // entityManager.clear(); // Zamknięcie bieżącej sesji
        PatientEntity patient2 = patientRepository.findById(patient.getId()).get();

        // Modyfikacja encji w pierwszej transakcji
        patient1.setLastName("Updated by Transaction 1");
        patientRepository.save(patient1);
        entityManager.flush();

        // Próba modyfikacji encji w drugiej transakcji
        assertThrows(ObjectOptimisticLockingFailureException.class, () -> {
            patient2.setLastName("Updated by Transaction 2");
            patientRepository.save(patient2);
            entityManager.flush(); // Wymuszenie zapisu do bazy
        });
    }


}
