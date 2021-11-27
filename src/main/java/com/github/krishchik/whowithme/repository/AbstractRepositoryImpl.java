package com.github.krishchik.whowithme.repository;

import com.github.krishchik.whowithme.api.repository.AbstractRepository;
import com.github.krishchik.whowithme.model.AbstractEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractRepositoryImpl<T extends AbstractEntity, Id> implements AbstractRepository<T, Id> {

    @PersistenceContext
    protected EntityManager entityManager;
    protected Class<T> entityClass;


    @Override
    public T getById(Id id) {
        return entityManager.find(getEntityClass(), id);
    }

    @Override
    public void save(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(T entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(T entity) {


        entityManager.remove(entity);
    }

    @Override
    public List<T> getAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(getEntityClass());
        Root<T> rootEntry = cq.from(getEntityClass());
        CriteriaQuery<T> all = cq.select(rootEntry);
        TypedQuery<T> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    protected abstract Class<T> getEntityClass();

}
