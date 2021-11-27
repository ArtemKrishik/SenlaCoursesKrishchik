package com.github.krishchik.whowithme.controller.Mapper;

import java.util.List;

public interface ModelMapperInterface<T, W> {

    W toEntity(T dto, Class<?> entity);

    T toDto(W entity, Class<?> dto);

    List<T> listToDto(List<W> listOfEntities, Class<?> dto);

    List<W> listToEntities(List<T> listOfDto, Class<?> entity);

}
