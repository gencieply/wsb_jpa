package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class PatientRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<PatientEntity> findPatientsByLastName(String lastName) {
        return entityManager.createQuery("SELECT p FROM PatientEntity p WHERE p.lastName = :lastName", PatientEntity.class)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    public List<PatientEntity> findPatientsWithMoreThanXVisits(Long visitCount) {
        return entityManager.createQuery(
                        "SELECT p FROM PatientEntity p " +
                                "JOIN p.visits v " +
                                "GROUP BY p.id " +
                                "HAVING COUNT(v.id) > :visitCount", PatientEntity.class)
                .setParameter("visitCount", visitCount)
                .getResultList();
    }

    public List<PatientEntity> findPatientsByRegistrationDateAfter(LocalDate date) {
        return entityManager.createQuery("SELECT p FROM PatientEntity p WHERE p.registrationDate > :date", PatientEntity.class)
                .setParameter("date", date)
                .getResultList();
    }

    public void save(PatientEntity patient) {
        if (patient.getId() == null) {
            entityManager.persist(patient);
        } else {
            entityManager.merge(patient);
        }
    }

    public Optional<PatientEntity> findById(Long id) {
        PatientEntity patient = entityManager.find(PatientEntity.class, id);
        return Optional.ofNullable(patient);
    }

    public void deleteById(Long id) {
        Optional<PatientEntity> patient = findById(id);
        if (patient != null) {
            entityManager.remove(patient);
        }
    }
}
