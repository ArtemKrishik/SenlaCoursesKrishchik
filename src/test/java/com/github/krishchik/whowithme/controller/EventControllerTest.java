package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.WebApplicationTest;
import com.github.krishchik.whowithme.api.repository.EventRepository;
import com.github.krishchik.whowithme.api.repository.PlaceRepository;
import com.github.krishchik.whowithme.model.Event;
import com.github.krishchik.whowithme.model.EventStatus;
import com.github.krishchik.whowithme.model.Place;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import javax.transaction.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class EventControllerTest extends WebApplicationTest {

    @Autowired
    private EventRepository eventRepository;

    private final Event event = Event.builder().id(1l).eventName("name").eventStatus(EventStatus.ACTIVE).ageLimit(12)
            .numberOfPeople(10).build();

    @Test
    public void eventShouldBeCreated() throws Exception {

        assertEquals(0, eventRepository.getAll().size());
        
        final String eventDto = """  
                        {
                           "id": 1,
                           "eventName": "event",
                           "eventStatus": "ACTIVE",
                           "numberOfPeople": 120,
                           "ageLimit": 12
                        }
                """;
        mockMvc.perform(
                post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventDto)
        ).andExpect(status().is2xxSuccessful());

        assertNotNull(eventRepository.getById(1l));
    }

    @Test
    public void eventShouldBeDeleted() throws Exception {
        eventRepository.save(event);

        final String eventUpdateDto = String.format("""
                {
                           "id": 1,
                           "eventName": "event",
                           "eventStatus": "ACTIVE",
                           "numberOfPeople": 120,
                           "ageLimit": 12
                }
                """);
        mockMvc.perform(
                delete("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventUpdateDto)
        ).andExpect(status().is2xxSuccessful());

        final Event event1 = eventRepository.getById(event.getId());

        assertNull(event1);
    }

    @Test
    public void eventShouldReturnWithCorrectFields() throws Exception {
        eventRepository.save(event);

        mockMvc.perform(
                get("/events/" + event.getId())
        ).andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(event.getId()))
                .andExpect(jsonPath("$.eventName").value(event.getEventName()))
                .andExpect(jsonPath("$.numberOfPeople").value(event.getNumberOfPeople()));
    }

    @Test
    public void eventNameShouldBeUpdated() throws Exception {
        eventRepository.save(event);

        final String eventUpdateDto = String.format("""
                {
                           "id": 1,
                           "eventName": "event",
                           "eventStatus": "ACTIVE",
                           "numberOfPeople": 50,
                           "ageLimit": 12
                }
                """);

        mockMvc.perform(
                put("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventUpdateDto)
        ).andExpect(status().is2xxSuccessful());

        final Event smith = eventRepository.getById(1l);
        assertEquals(smith.getNumberOfPeople(), 50);
    }

    @Test
    public void shouldReturnErrorTextWhenEventNotExists() throws Exception {
        mockMvc.perform(
                get("/events/12")
        ).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("event with id 12 wasn`t found"));
    }

}
