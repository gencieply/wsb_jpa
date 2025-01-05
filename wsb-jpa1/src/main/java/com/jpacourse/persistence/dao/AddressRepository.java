package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.AddressEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class AddressRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(AddressEntity address) {
        if (address.getId() == null) {
            entityManager.persist(address);
        } else {
            entityManager.merge(address);
        }
    }

    public AddressEntity findById(Long id) {
        return entityManager.find(AddressEntity.class, id);
    }

    public List<AddressEntity> findAll() {
        return entityManager.createQuery("SELECT a FROM AddressEntity a", AddressEntity.class).getResultList();
    }

    public void delete(Long id) {
        AddressEntity address = findById(id);
        if (address != null) {
            entityManager.remove(address);
        }
    }
}
