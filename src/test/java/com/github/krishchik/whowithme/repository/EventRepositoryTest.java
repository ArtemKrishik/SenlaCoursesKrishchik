package com.github.krishchik.whowithme.repository;

import com.github.krishchik.whowithme.RepositoryTest;
import com.github.krishchik.whowithme.model.*;
import com.github.krishchik.whowithme.repository.repositoryApi.*;
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
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebAppConfiguration
public class EventRepositoryTest extends RepositoryTest {

    @Autowired
    EventCrudRepository eventRepository;
    @Autowired
    UserCrudRepository userRepository;
    @Autowired
    PlaceCrudRepository placeRepository;
    @Autowired
    ProfileCrudRepository profileRepository;
    @Autowired
    RoleCrudRepository roleRepository;

    private Event event1;
    private User user;
    private Profile profile;
    private Role role;
    private Place place1;

    @BeforeAll
    public void getTestEvent() throws Exception {

        place1 = new Place();
        place1.setId(1l);
        place1.setPlaceName("name");
        place1.setCapacity(10);
        place1.setPrice(100);

        user = new User();
        user.setId(2l);
        user.setLogin("login");
        user.setPassword("password");
        profile = new Profile();
        profile.setId(1l);
        profile.setAge(10);
        profile.setName("Name");
        profile.setPhoneNumber(111l);
        role = new Role();
        role.setId(1l);
        role.setName("admin");

        event1 = new Event();
        event1.setEventName("1event");
        event1.setEventStatus(EventStatus.ACTIVE);
        event1.setAvailableSlots(10);
        event1.setNumberOfSlots(10);
        event1.setAgeLimit(10);
        event1.setId(1l);
        event1.setStartTime(new Timestamp(System.currentTimeMillis()));
        event1.setEndTime(new Timestamp(System.currentTimeMillis()));

    }

    @Test
    public void shouldGiveEventsByPlace() throws Exception {
        placeRepository.save(place1);
        profileRepository.save(profile);
        roleRepository.save(role);
        user.setProfile(profile);
        user.setRole(role);
        userRepository.save(user);
        event1.setPlace(place1);
        event1.setCreator(user);
        eventRepository.save(event1);
        Pageable pageable = PageRequest.of(0,3);
        final Page<Event> events = eventRepository.findEventsByPlaceId(pageable, place1.getId());
        assertEquals("1event", events.getContent().get(0).getEventName());
        assertEquals(1, events.getTotalElements());
    }

    @Test
    void shouldGetEventByIdCorrect() throws Exception {
        placeRepository.save(place1);
        profileRepository.save(profile);
        roleRepository.save(role);
        user.setProfile(profile);
        user.setRole(role);
        userRepository.save(user);
        event1.setPlace(place1);
        event1.setCreator(user);
        eventRepository.save(event1);
        eventRepository.getById(1l);
        assertEquals(1l, eventRepository.getById(1l).getId());
    }


    @Test void shouldDeleteEventCorrect() throws Exception {
        placeRepository.save(place1);
        profileRepository.save(profile);
        roleRepository.save(role);
        user.setProfile(profile);
        user.setRole(role);
        userRepository.save(user);
        event1.setPlace(place1);
        event1.setCreator(user);
        eventRepository.save(event1);
        eventRepository.delete(event1);
        assertThrows(
                JpaObjectRetrievalFailureException.class,
                () -> eventRepository.getById(1l)
        );
    }

    @Test
    public void shouldReturnAllEvents() throws Exception {
        placeRepository.save(place1);
        profileRepository.save(profile);
        roleRepository.save(role);
        user.setProfile(profile);
        user.setRole(role);
        userRepository.save(user);
        event1.setPlace(place1);
        event1.setCreator(user);
        eventRepository.save(event1);
        Pageable pageable = PageRequest.of(0,3);
        final Page<Event> events = eventRepository.findAll(pageable);
        assertEquals(1L, events.getTotalElements());
        assertEquals("1event", events.getContent().get(0).getEventName());

    }

    @Test
    public void shouldUpdateEventCorrect() throws Exception {
        placeRepository.save(place1);
        profileRepository.save(profile);
        roleRepository.save(role);
        user.setProfile(profile);
        user.setRole(role);
        userRepository.save(user);
        event1.setPlace(place1);
        event1.setCreator(user);
        eventRepository.save(event1);

        Event updatedEvent = eventRepository.getById(1l);
        updatedEvent.setEventName("newName");
        eventRepository.save(updatedEvent);
        assertEquals("newName", eventRepository.getById(1l).getEventName());
    }
}
