package com.github.krishchik.whowithme.controller.Mapper;

import com.github.krishchik.whowithme.controller.dto.PlaceDto;
import com.github.krishchik.whowithme.model.Place;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class Mapper<T, W> implements ModelMapperInterface<T, W> {

    private final ModelMapper mapper;

    @Override
    public W toEntity(T dto, Class<?> entity) {
        return Objects.isNull(dto) ? null : (W) mapper.map(dto, entity);
    }
    @Override
    public T toDto(W entity, Class<?> dto) {
        return Objects.isNull(entity) ? null : (T) mapper.map(entity, dto);
    }

    @Override
    public List<T> listToDto(List<W> listOfEntities, Class<?> dto) {
        return listOfEntities.stream()
                .map((entity) -> (T) mapper.map(entity, dto))
                .collect(Collectors.toList());
    }

    @Override
    public List<W> listToEntities(List<T> listOfDto, Class<?> entity) {
        return listOfDto.stream()
                .map((dto) -> (W) mapper.map(dto, entity))
                .collect(Collectors.toList());
    }


}
