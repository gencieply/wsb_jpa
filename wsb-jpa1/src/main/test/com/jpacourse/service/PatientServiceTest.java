package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.persistence.dao.AddressDao;
import com.jpacourse.persistence.dao.DoctorDao;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.service.PatientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorDao doctorDao;

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private AddressDao addressDao;

    @Transactional
    @Test
    public void testShouldDeletePatientWithCascadeVisits() {

    }

    @Transactional
    @Test
    public void testShouldFindPatientById() {
        PatientEntity patientEntity = new PatientEntity();

        patientEntity.setFirstName("John");
        patientEntity.setLastName("Doe");
        patientEntity.setTelephoneNumber("123123123");
        patientEntity.setDateOfBirth(LocalDate.of(1990, 5, 15));
        patientEntity.setPatientNumber("TEST11");

        AddressEntity address = new AddressEntity();
        address.setCity("Warsaw");
        address.setAddressLine1("Main Street 123");
        address.setAddressLine2("Apartment 45");
        address.setPostalCode("00-001");

        patientEntity.setAddress(address);

        patientDao.save(patientEntity);

        PatientTO patientTO = patientService.getPatientById(patientEntity.getId());

        assertThat(patientTO).isNotNull();
        assertThat(patientTO.getFirstName()).isEqualTo("Jane");
        assertThat(patientTO.getLastName()).isEqualTo("Smith");
        assertThat(patientTO.getTelephoneNumber()).isEqualTo("987654321");
        assertThat(patientTO.getDateOfBirth()).isEqualTo(patientEntity.getDateOfBirth());
    }
}