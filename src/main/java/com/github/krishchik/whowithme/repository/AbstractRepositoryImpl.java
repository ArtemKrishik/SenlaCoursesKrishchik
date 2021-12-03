package com.github.krishchik.whowithme.repository;

import com.github.krishchik.whowithme.api.repository.AbstractRepository;
import com.github.krishchik.whowithme.model.AbstractEntity;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
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
        entityManager.remove(entityManager.find(getEntityClass(), entity.getId()));
    }

    @Override
    public List<T> getAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(getEntityClass());
        cq.from(getEntityClass());
        return entityManager.createQuery(cq).getResultList();
    }

    protected abstract Class<T> getEntityClass();

}
