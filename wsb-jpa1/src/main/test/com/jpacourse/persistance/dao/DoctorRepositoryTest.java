package com.jpacourse.persistance.dao;

import com.jpacourse.persistence.dao.PatientRepository;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.enums.Specialization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@SpringBootTest
@Transactional
public class DoctorRepositoryTest {


    private void handleOptimisticLockException(Runnable action) {
        try {
            action.run();
        } catch (OptimisticLockException e) {
            throw new ObjectOptimisticLockingFailureException(DoctorEntity.class, e);
        }
    }
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private EntityManager entityManager2;

    @Test
    @Transactional
    public void testOptimisticLocking() {
        DoctorEntity doktor = new DoctorEntity();
        doktor.setFirstName("Marek");
        doktor.setLastName("Nowak");
        doktor.setTelephoneNumber("111111111");
        doktor.setEmail("mark@gmail.com");
        doktor.setDoctorNumber("d123");
        doktor.setSpecialization(Specialization.SURGEON);
        entityManager.persist(doktor);
        entityManager.flush();

        DoctorEntity doctor1 = entityManager.find(DoctorEntity.class, doktor.getId());
        DoctorEntity doctor2 = entityManager.find(DoctorEntity.class, doktor.getId());

        entityManager.detach(doctor1);
        entityManager.detach(doctor2);

        doctor1.setLastName("Transaction 1");
        entityManager.merge(doctor1);
        entityManager.flush();

        doctor2.setLastName("Transaction 2");

        assertThrows(ObjectOptimisticLockingFailureException.class, () -> {
            handleOptimisticLockException(() -> {
                entityManager.merge(doctor2);
                entityManager.flush();
            });
        });
    }

}
    
