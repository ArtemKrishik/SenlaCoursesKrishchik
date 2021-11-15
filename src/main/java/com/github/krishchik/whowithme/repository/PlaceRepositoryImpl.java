package com.github.krishchik.whowithme.repository;

import com.github.krishchik.whowithme.api.repository.PlaceRepository;
import com.github.krishchik.whowithme.model.Place;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class PlaceRepositoryImpl implements PlaceRepository {


    private List<Place> repository = new ArrayList<>();

    @Override
    public Place getById(Long id) {
        for (Place entity : repository) {
            if(id.equals(entity.getId())) {
                return entity;
            }
        }
        return null;
    }

    @Override
    public void save(Place entity) {
        repository.add(entity);
    }



    @Override
    public void delete(Long id) {
        repository.remove(id);
    }


    @Override
    public List<Place> getAll() {
        return new ArrayList<>(repository);
    }




    @Override
    public void update(Place updatedPlace) {
        Place place = getById(updatedPlace.getId());
        place.setPlaceName(updatedPlace.getPlaceName());
        place.setCapacity(updatedPlace.getCapacity());
        place.setPrice(updatedPlace.getPrice());
    }

}
