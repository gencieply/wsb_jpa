package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.DoctorEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class DoctorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(DoctorEntity doctor) {
        if (doctor.getId() == null) {
            entityManager.persist(doctor);
        } else {
            entityManager.merge(doctor);
        }
    }

    public DoctorEntity findById(Long id) {
        return entityManager.find(DoctorEntity.class, id);
    }

    public List<DoctorEntity> findAll() {
        return entityManager.createQuery("SELECT d FROM DoctorEntity d", DoctorEntity.class).getResultList();
    }

    public void delete(Long id) {
        DoctorEntity doctor = findById(id);
        if (doctor != null) {
            entityManager.remove(doctor);
        }
    }
}
