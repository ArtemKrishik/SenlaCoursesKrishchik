package com.github.krishchik.whowithme.api.repository;

import com.github.krishchik.whowithme.model.Place;
import com.github.krishchik.whowithme.model.User;

import java.util.List;

public interface PlaceRepository extends AbstractRepository<Place, Long>{

    List<Place> getPlacesSortedByCapacity();

    List<Place> getThreeCheapestPlaces();


}
