package com.github.krishchik.whowithme.repository;

import com.github.krishchik.whowithme.api.repository.AbstractRepository;
import com.github.krishchik.whowithme.model.AbstractEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AbstractRepositoryImpl<T extends AbstractEntity> implements AbstractRepository<T> {

    private List<T> repository = new ArrayList<>();

    @Override
    public T getById(Long id) {
        for (T entity : repository) {
            if(id.equals(entity.getId())) {
                return entity;
            }
        }
        return null;
    }

    @Override
    public void save(T entity) {
        repository.add(entity);
    }

    @Override
    public void update(T entity) {

    }

    @Override
    public void delete(T entity) {
        repository.remove(entity);
    }


    @Override
    public List<T> getAll() {
        return new ArrayList<>(repository);
    }

    @Override
    public Long getMaxId() {
        if (!repository.isEmpty()) {
            List<Long> ids = new ArrayList<>();
            for (T entity : repository) {
                ids.add(entity.getId());
            }
            return Collections.max(ids);
        } else {
            return 0L;
        }
    }
}
