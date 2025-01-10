package com.jpacourse.persistance.dao;

import com.jpacourse.persistence.dao.DoctorRepository;
import com.jpacourse.persistence.dao.PatientRepository;
import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.enums.Specialization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.OptimisticLockException;
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

    }

