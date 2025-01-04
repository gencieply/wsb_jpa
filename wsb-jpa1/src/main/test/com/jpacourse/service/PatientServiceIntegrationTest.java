package com.jpacourse.service;
import com.jpacourse.dto.PatientTO;
import com.jpacourse.persistence.dao.PatientRepository;
import com.jpacourse.persistence.dao.VisitRepository;
import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.persistence.enums.Specialization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PatientServiceIntegrationTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private VisitRepository visitRepository;

    @Test
    public void testGetPatientById_ShouldReturnPatientTO() {
        // given
        AddressEntity address = new AddressEntity();
        address.setAddressLine1("123 Main St");
        address.setCity("New York");
        address.setPostalCode("10001");

        DoctorEntity doctor = new DoctorEntity();
        doctor.setFirstName("John");
        doctor.setLastName("Doe");
        doctor.setTelephoneNumber("555-1234");
        doctor.setSpecialization(Specialization.SURGEON);

        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Alice");
        patient.setLastName("Smith");
        patient.setDateOfBirth(LocalDate.of(1990, 3, 25));
        patient.setRegistrationDate(LocalDate.of(2023, 1, 15));
        patient.setAddress(address);

        VisitEntity visit = new VisitEntity();
        visit.setDescription("Routine checkup");
        visit.setTime(LocalDateTime.of(2024, 12, 5, 10, 0));
        visit.setDoctor(doctor);
        visit.setPatient(patient);

        patientRepository.save(patient);
        visitRepository.save(visit);

        // when
        PatientTO patientTO = patientService.getPatientById(patient.getId());

        // then
        assertThat(patientTO).isNotNull();
        assertThat(patientTO.getFirstName()).isEqualTo("Alice");
        assertThat(patientTO.getVisits()).hasSize(1);
        assertThat(patientTO.getVisits().get(0).getDoctorFirstName()).isEqualTo("John");
        assertThat(patientTO.getVisits().get(0).getTime()).isEqualTo(LocalDateTime.of(2024, 12, 5, 10, 0));
    }

    @Test
    public void testDeletePatient_ShouldCascadeRemoveVisits() {
        // given
        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Bob");
        patient.setLastName("Johnson");
        patient.setDateOfBirth(LocalDate.of(1985, 6, 10));
        patient.setRegistrationDate(LocalDate.of(2023, 2, 20));
        patientRepository.save(patient);

        VisitEntity visit = new VisitEntity();
        visit.setDescription("Skin rash consultation");
        visit.setTime(LocalDateTime.of(2024, 12, 6, 14, 0));
        visit.setPatient(patient);
        visitRepository.save(visit);

        Long patientId = patient.getId();
        Long visitId = visit.getId();

        // when
        patientService.deletePatient(patientId);

        // then
        assertThat(patientRepository.findById(patientId)).isEmpty();
        assertThat(visitRepository.findById(visitId)).isEmpty();
    }
}
