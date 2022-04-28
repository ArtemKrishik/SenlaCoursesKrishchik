package com.github.krishchik.whowithme.controller.mapper.mapperImpl;

import com.github.krishchik.whowithme.controller.dto.PlaceDto;
import com.github.krishchik.whowithme.controller.mapper.IPlaceMapper;
import com.github.krishchik.whowithme.model.Place;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class PlaceMapper implements IPlaceMapper {

    private final ModelMapper mapper;

    @Override
    public Place toEntity(PlaceDto dto) {
        return Objects.isNull(dto)
                ? null
                : mapper.map(dto, Place.class);
    }

    @Override
    public PlaceDto toDto(Place entity) {
        return Objects.isNull(entity)
                ? null
                : mapper.map(entity, PlaceDto.class);
    }
}
