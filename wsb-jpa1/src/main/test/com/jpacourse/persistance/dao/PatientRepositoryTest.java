package com.jpacourse.persistance.dao;

import com.jpacourse.persistence.dao.PatientRepository;
import com.jpacourse.persistence.entity.PatientEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

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
        int visitCount = 1;

        // when
        List<PatientEntity> patients = patientRepository.findPatientsWithMoreThanXVisits(visitCount);

        // then
        assertThat(patients).isNotEmpty();
        assertThat(patients.size()).isGreaterThan(0);

        // dodatkowe sprawdzenie
        patients.forEach(patient -> {
            assertThat(patient.getVisits().size()).isGreaterThan(visitCount);
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
