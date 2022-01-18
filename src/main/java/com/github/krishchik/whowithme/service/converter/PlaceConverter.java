package com.github.krishchik.whowithme.service.converter;

import com.github.krishchik.whowithme.controller.Mapper.IPlaceMapper;
import com.github.krishchik.whowithme.controller.dto.PlaceDto;
import com.github.krishchik.whowithme.model.Place;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PlaceConverter {

    private final IPlaceMapper eventMapper;

    public Place toEntity(PlaceDto placeDto){
        return eventMapper.toEntity(placeDto);
    }

    public PlaceDto toDto(Place place){
        return eventMapper.toDto(place);
    }
    }


