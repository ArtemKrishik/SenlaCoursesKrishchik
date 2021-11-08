package com.github.krishchik.whowithme.api.repository;

import com.github.krishchik.whowithme.model.AbstractEntity;

import java.util.List;

public interface AbstractRepository<T extends AbstractEntity> {

    T getById(Long id);

    void save(T entity);

    void update(T entity);

    void delete(T entity);

    Long getMaxId();

    List<T> getAll();

}
