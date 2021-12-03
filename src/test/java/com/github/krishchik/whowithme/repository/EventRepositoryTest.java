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
import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = { PlaceRepositoryImpl.class, EventRepositoryImpl.class, UserRepositoryImpl.class, ProfileRepositoryImpl.class, RoleRepositoryImpl.class})
public class EventRepositoryTest extends RepositoryTest {

    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PlaceRepository placeRepository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    RoleRepository roleRepository;


    private Event event1;
    private User user;
    private Profile profile;
    private Role role;
    private Place place1;

    @BeforeAll
    public void getTestEvent() throws Exception {

        place1 = new Place();
        place1.setId(1l);
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
        event1.setNumberOfPeople(10);
        event1.setAgeLimit(10);
        event1.setId(1l);
        event1.setDate(LocalDateTime.now());
        event1.setStartTime(LocalDateTime.now());

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

        List<Event> eventsByPlace = eventRepository.getEventsByPlace(place1.getId());
        assertEquals("1event",eventsByPlace.get(0).getEventName());
        assertEquals(1, eventsByPlace.size());

    }

    @Test
    void shouldGetEventByIdCorrect() throws Exception {
        eventRepository.save(event1);
        eventRepository.getById(1l);
        assertEquals(1l, eventRepository.getById(1l).getId());
    }

    @Test void shouldDeleteEventCorrect() throws Exception {
        eventRepository.save(event1);
        eventRepository.delete(event1);
        assertNull(eventRepository.getById(1l));
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
        final List<Event> events = eventRepository.getAll();
        assertEquals(1, events.size());
        assertEquals("1event", events.get(0).getEventName());

    }

    @Test
    public void shouldUpdateEventCorrect() throws Exception {
        eventRepository.save(event1);
        Event updatedEvent = eventRepository.getById(1l);
        updatedEvent.setEventName("newName");
        eventRepository.update(updatedEvent);
        assertEquals("newName", eventRepository.getById(1l).getEventName());
    }




}
