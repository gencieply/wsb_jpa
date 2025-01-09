package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class VisitRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<VisitEntity> findVisitsByPatientId(Long patientId) {
        return entityManager.createQuery("SELECT v FROM VisitEntity v WHERE v.patient.id = :patientId", VisitEntity.class)
                .setParameter("patientId", patientId)
                .getResultList();
    }
    public VisitEntity save(VisitEntity visit) {
        if (visit.getId() == null) {
            entityManager.persist(visit);
            return visit;
        } else {
            return entityManager.merge(visit);
        }
    }
}
