package com.github.krishchik.whowithme.api.service;

import com.github.krishchik.whowithme.controller.dto.EventDto;
import com.github.krishchik.whowithme.controller.dto.PlaceDto;
import com.github.krishchik.whowithme.model.Place;

import java.util.List;

public interface PlaceService {

    void createPlace(PlaceDto createdPlace) throws Exception;

    void updatePlace(PlaceDto updatedPlace) throws Exception;

    PlaceDto getPlaceById(Long placeId) throws Exception;

    void deletePlace(PlaceDto deletedPlace) throws Exception;

    List<PlaceDto> getAllPlaces() throws Exception;

    List<PlaceDto> getPlacesSortedByCapacity();

    List<PlaceDto> getThreeCheapestPlaces();

}
