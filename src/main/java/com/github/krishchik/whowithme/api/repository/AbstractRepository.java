package com.github.krishchik.whowithme.api.repository;

import com.github.krishchik.whowithme.model.AbstractEntity;

import java.util.List;

public interface AbstractRepository<T extends AbstractEntity> {

    T getById(Long id) throws Exception;

    void save(T entity) throws Exception;

    void update(T entity) throws Exception;

    void delete(Long id) throws Exception;



    List<T> getAll() throws Exception;

}
