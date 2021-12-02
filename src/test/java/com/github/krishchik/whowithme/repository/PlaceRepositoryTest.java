package com.github.krishchik.whowithme.repository;

import com.github.krishchik.whowithme.RepositoryTest;
import com.github.krishchik.whowithme.api.repository.*;
import com.github.krishchik.whowithme.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = { PlaceRepositoryImpl.class, EventRepositoryImpl.class})
public class PlaceRepositoryTest extends RepositoryTest {

    @Autowired
    PlaceRepository placeRepository;

    @Mock
    private Place place;

    @BeforeAll
    public void getTestPlace() throws Exception {

        place = new Place();
        place.setId(1l);
        place.setCapacity(10);
        place.setPrice(100);

    }


    @Test void shouldDeletePlaceCorrect() throws Exception {
        placeRepository.save(place);
        placeRepository.delete(place);
        assertNull(placeRepository.getById(1l));
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
        final List<Place> places = placeRepository.getAll();
        assertEquals(1, places.size());
        assertEquals(100, places.get(0).getPrice());

    }

    @Test
    public void shouldUpdatePlaceCorrect() throws Exception {
        placeRepository.save(place);
        Place updatedPlace = placeRepository.getById(1l);
        updatedPlace.setPrice(120);
        placeRepository.update(updatedPlace);
        assertEquals(120, placeRepository.getById(1l).getPrice());
    }

    @Test
    public void shouldGivePlacesOrderedByCapacity() throws Exception {
        final Place place1 = new Place();
        final Place place2 = new Place();
        final Place place3 = new Place();
        place1.setId(1l);
        place1.setCapacity(10);
        place1.setPrice(100);
        placeRepository.save(place1);
        place2.setId(2l);
        place2.setCapacity(3);
        place2.setPrice(100);
        placeRepository.save(place2);
        place3.setId(3l);
        place3.setCapacity(110);
        place3.setPrice(100);
        placeRepository.save(place3);
        List<Place> orderedPlaces = placeRepository.getPlacesSortedByCapacity();
        assertEquals(3, orderedPlaces.get(0).getCapacity());
        assertEquals(10, orderedPlaces.get(1).getCapacity());
        assertEquals(110, orderedPlaces.get(2).getCapacity());

    }

    @Test
    public void shouldGiveThreeCheapestPlaces() throws Exception {
        final Place place1 = new Place();
        final Place place2 = new Place();
        final Place place3 = new Place();
        final Place place4 = new Place();

        place1.setId(1l);
        place1.setCapacity(10);
        place1.setPrice(100);
        placeRepository.save(place1);
        place2.setId(2l);
        place2.setCapacity(3);
        place2.setPrice(300);
        placeRepository.save(place2);
        place3.setId(3l);
        place3.setCapacity(110);
        place3.setPrice(200);
        placeRepository.save(place3);
        place4.setId(4l);
        place4.setCapacity(110);
        place4.setPrice(1100);
        placeRepository.save(place4);
        List<Place> orderedPlaces = placeRepository.getThreeCheapestPlaces();
        assertNotEquals(1100, orderedPlaces.get(0).getPrice());
        assertNotEquals(1100, orderedPlaces.get(1).getPrice());
        assertNotEquals(1100, orderedPlaces.get(2).getPrice());
        assertEquals(3, orderedPlaces.size());
    }


}
