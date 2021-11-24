package com.github.krishchik.whowithme.api.repository;

import com.github.krishchik.whowithme.model.AbstractEntity;

import java.util.List;

public interface AbstractRepository<T extends AbstractEntity, Id> {

    T getById(Id id) throws Exception;

    void save(T entity) throws Exception;

    void update(T entity) throws Exception;

    void delete(T entity) throws Exception;

    List<T> getAll() throws Exception;

}
