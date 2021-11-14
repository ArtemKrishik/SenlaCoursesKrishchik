package com.github.krishchik.whowithme.api.service;

import com.github.krishchik.whowithme.model.Place;

import java.util.List;

public interface PlaceService {

    void createPlace(Place createdPlace) throws Exception;

    void updatePlace(Place updatedPlace) throws Exception;

    Place getPlaceById(Long placeId) throws Exception;

    void deletePlace(Place deletedPlace) throws Exception;

    List<Place> getAllPlaces() throws Exception;

}
