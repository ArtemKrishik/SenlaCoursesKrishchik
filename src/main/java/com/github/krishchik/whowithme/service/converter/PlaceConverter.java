package com.github.krishchik.whowithme.service.converter;

import com.github.krishchik.whowithme.controller.Mapper.Mapper;
import com.github.krishchik.whowithme.controller.dto.PlaceDto;
import com.github.krishchik.whowithme.controller.dto.ProfileDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.model.Place;
import com.github.krishchik.whowithme.model.Profile;
import com.github.krishchik.whowithme.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PlaceConverter {

    private final Mapper<PlaceDto, Place> placeMapper;

    public Place toEntity(PlaceDto placeDto){
        return placeMapper.toEntity(placeDto, Place.class);
    }

    public PlaceDto toDto(Place place){
        return placeMapper.toDto(place, PlaceDto.class);
    }

    public List<PlaceDto> listToDto(List<Place> places) {
        return placeMapper.listToDto(places, PlaceDto.class);
    }

}
