package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.persistence.dao.AddressRepository;
import com.jpacourse.persistence.dao.DoctorRepository;
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
    private AddressRepository addressRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private VisitRepository visitRepository;

    @Test
    public void testGetPatientById_ShouldReturnPatientTO() {
        // given
        AddressEntity patientAddress = new AddressEntity();
        patientAddress.setAddressLine1("123 Main St");
        patientAddress.setCity("New York");
        patientAddress.setPostalCode("10001");
        addressRepository.save(patientAddress);

        AddressEntity doctorAddress = new AddressEntity();
        doctorAddress.setAddressLine1("456 Elm St");
        doctorAddress.setCity("Los Angeles");
        doctorAddress.setPostalCode("90001");
        addressRepository.save(doctorAddress);

        DoctorEntity doctor = new DoctorEntity();
        doctor.setFirstName("John");
        doctor.setLastName("Doe");
        doctor.setTelephoneNumber("555-1234");
        doctor.setEmail("john.doe@example.com");
        doctor.setDoctorNumber("D004");
        doctor.setSpecialization(Specialization.SURGEON);
        doctor.setAddress(doctorAddress);
        doctorRepository.save(doctor);

        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Alice");
        patient.setLastName("Smith");
        patient.setPatientNumber("P003");
        patient.setTelephoneNumber("555-9876");
        patient.setDateOfBirth(LocalDate.of(1990, 3, 25));
        patient.setRegistrationDate(LocalDate.of(2023, 1, 15));
        patient.setAddress(patientAddress);
        patientRepository.save(patient);

        VisitEntity visit = new VisitEntity();
        visit.setDescription("Routine checkup");
        visit.setTime(LocalDateTime.of(2024, 12, 5, 10, 0));
        visit.setDoctor(doctor);
        visit.setPatient(patient);
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

}
