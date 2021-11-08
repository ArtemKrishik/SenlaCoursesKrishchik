package com.github.krishchik.whowithme.api.service;

import com.github.krishchik.whowithme.model.Place;

import java.util.List;

public interface PlaceService {

    void createPlace(Place createdPlace);

    void updatePlace(Place updatedPlace);

    Place getPlaceById(Long placeId);

    void deletePlace(Place deletedPlace);

    List<Place> getAllPlaces();

}
