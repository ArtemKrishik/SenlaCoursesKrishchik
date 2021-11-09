package com.github.krishchik.whowithme.repository;

import com.github.krishchik.whowithme.api.repository.PlaceRepository;
import com.github.krishchik.whowithme.model.Place;
import org.springframework.stereotype.Repository;

@Repository
public class PlaceRepositoryImpl extends AbstractRepositoryImpl<Place> implements PlaceRepository {


    @Override
    public void update(Place updatedPlace) {
        Place place = getById(updatedPlace.getId());
        place.setPlaceName(updatedPlace.getPlaceName());
        place.setCapacity(updatedPlace.getCapacity());
        place.setPrice(updatedPlace.getPrice());
    }

}
