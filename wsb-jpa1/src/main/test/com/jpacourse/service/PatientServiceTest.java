package com.jpacourse.service;

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
import java.util.List;
import com.jpacourse.persistence.dao.AddressRepository;
import com.jpacourse.persistence.dao.DoctorRepository;
import com.jpacourse.persistence.dao.PatientRepository;
import com.jpacourse.persistence.dao.VisitRepository;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PatientServiceTest {

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
    public void testGetVisitsByPatientId() {
        // given
        AddressEntity address = new AddressEntity();
        address.setAddressLine1("123 Main St");
        address.setCity("New York");
        address.setPostalCode("10001");
        addressRepository.save(address);

        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Alice");
        patient.setLastName("Smith");
        patient.setPatientNumber("P011");
        patient.setTelephoneNumber("555-9876");
        patient.setDateOfBirth(LocalDate.of(1990, 3, 25));
        patient.setRegistrationDate(LocalDate.of(2023, 1, 15));
        patient.setAddress(address);
        patientRepository.save(patient);

        DoctorEntity doctor = new DoctorEntity();
        doctor.setFirstName("John");
        doctor.setLastName("Doe");
        doctor.setTelephoneNumber("555-1234");
        doctor.setEmail("john.doe@example.com");
        doctor.setDoctorNumber("D011");
        doctor.setSpecialization(Specialization.SURGEON);
        doctor.setAddress(address);
        doctorRepository.save(doctor);

        VisitEntity visit = new VisitEntity();
        visit.setDescription("Routine checkup");
        visit.setTime(LocalDateTime.of(2024, 12, 5, 10, 0));
        visit.setDoctor(doctor);
        visit.setPatient(patient);
        visitRepository.save(visit);

        // when
        List<VisitEntity> visits = patientService.getVisitsByPatientId(patient.getId());

        // then
        assertThat(visits).isNotEmpty();
        assertThat(visits.get(0).getPatient().getId()).isEqualTo(patient.getId());
    }
}