package com.github.krishchik.whowithme.repository;

import com.github.krishchik.whowithme.RepositoryTest;
import com.github.krishchik.whowithme.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.web.WebAppConfiguration;


import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebAppConfiguration
@Transactional
public class PlaceRepositoryTest extends RepositoryTest {

    @Autowired
    PlaceCrudRepository placeRepository;

    private Place place;

    @BeforeAll
    public void getTestPlace() throws Exception {

        place = new Place();
        place.setPlaceName("name");
        place.setId(1l);
        place.setCapacity(10);
        place.setPrice(100);

    }


    @Test
    void shouldDeletePlaceCorrect() throws Exception {
        placeRepository.save(place);
        placeRepository.delete(place);
        assertThrows(
                JpaObjectRetrievalFailureException.class,
                () -> placeRepository.getById(1l)
        );
    }

    @Test
    public void shouldFindPlaceByIdCorrect() throws Exception {
        placeRepository.save(place);
        final Place potentialPlace = placeRepository.getById(1l);
        assertEquals(place.getId(), potentialPlace.getId());
    }


    @Test
    public void shouldReturnAllPlaces() throws Exception {
        placeRepository.save(place);
        Pageable pageable = PageRequest.of(0,3);
        final Page<Place> places = placeRepository.findAll(pageable);
        assertEquals(1, places.getTotalElements());
        assertEquals(100, places.getContent().get(0).getPrice());

    }

    @Test
    public void shouldUpdatePlaceCorrect() throws Exception {
        placeRepository.save(place);
        Place updatedPlace = placeRepository.getById(1l);
        updatedPlace.setPrice(120);
        placeRepository.save(updatedPlace);
        assertEquals(120, placeRepository.getById(1l).getPrice());
    }
}
