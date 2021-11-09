package com.github.krishchik.whowithme.service;

import com.github.krishchik.whowithme.api.repository.PlaceRepository;
import com.github.krishchik.whowithme.api.service.PlaceService;
import com.github.krishchik.whowithme.model.Place;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;

    public PlaceServiceImpl(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override
    public void createPlace(Place createdPlace) {
        placeRepository.save(createdPlace);
    }

    @Override
    public void updatePlace(Place updatedPlace) {
        placeRepository.update(updatedPlace);
    }

    @Override
    public Place getPlaceById(Long placeId) {
        return placeRepository.getById(placeId);
    }

    @Override
    public void deletePlace(Place deletedPlace) {
        placeRepository.delete(deletedPlace);
    }

    @Override
    public List<Place> getAllPlaces() {
        return placeRepository.getAll();
    }
}
