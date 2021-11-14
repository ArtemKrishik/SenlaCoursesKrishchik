package com.github.krishchik.whowithme.service;

import com.github.krishchik.whowithme.annotation.Transactional;
import com.github.krishchik.whowithme.api.repository.PlaceRepository;
import com.github.krishchik.whowithme.api.service.PlaceService;
import com.github.krishchik.whowithme.model.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private final PlaceRepository placeRepository;

    public PlaceServiceImpl(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override
    public void createPlace(Place createdPlace) throws Exception {
        placeRepository.save(createdPlace);
    }

    @Override
    public void updatePlace(Place updatedPlace) throws Exception {
        placeRepository.update(updatedPlace);
    }

    @Override
    public Place getPlaceById(Long placeId) throws Exception {
        return placeRepository.getById(placeId);
    }

    @Override
    public void deletePlace(Place deletedPlace) throws Exception {
        placeRepository.delete(deletedPlace.getId());
    }

    @Override
    public List<Place> getAllPlaces() throws Exception {
        return placeRepository.getAll();
    }
}
