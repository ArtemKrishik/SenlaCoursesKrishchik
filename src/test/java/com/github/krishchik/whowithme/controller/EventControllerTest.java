package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.WebApplicationTest;
import com.github.krishchik.whowithme.model.Event;
import com.github.krishchik.whowithme.model.EventStatus;
import com.github.krishchik.whowithme.repository.EventCrudRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.security.test.context.support.WithMockUser;


import javax.transaction.Transactional;

import java.security.Principal;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
public class EventControllerTest extends WebApplicationTest {


    @Autowired
    private EventCrudRepository eventRepository;

    private final Event event = Event.builder().id(1l).eventName("event").eventStatus(EventStatus.ACTIVE).ageLimit(12).numberOfSlots(120)
            .build();

    @Autowired
    private EventController eventController;

    @Test
    @WithMockUser(username = "artem", roles = "ADMIN")
    public void eventShouldBeCreated() throws Exception {

        assertEquals(1, eventRepository.findAll().size());
        
        final String eventDto = """  
                        {
                           "eventName": "event",
                           "eventStatus": "ACTIVE",
                           "numberOfSlots": 120,
                           "availableSlots":50,
                           "ageLimit": 12,
                           "startTime": [
                                           2021,12,29,15,21,30,135000000
                                       ],
                                       "endTime": [
                                           2021,12,29,15,22,30,135000000
                                       ],
                           "placeId":1
                        }
                """;
        mockMvc.perform(
                post("/events/user")
                        .principal(new Principal() {
                            @Override
                            public String getName() {
                                return "artem";
                            }
                        })
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventDto)
        ).andExpect(status().is2xxSuccessful());

        assertEquals(2, eventRepository.findAll().size());

    }

    @Test
    @WithMockUser(username = "artem", roles = "ADMIN")
    public void eventShouldBeDeleted() throws Exception {


        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "artem";
            }
        };
        mockMvc.perform(
                delete("/events/user/1")
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(status().is2xxSuccessful());

        assertThrows(
                JpaObjectRetrievalFailureException.class,
                () -> eventRepository.getById(event.getId())
        );
    }

    @Test
    @WithMockUser(username = "artem", roles = "ADMIN")
    public void eventShouldReturnWithCorrectFields() throws Exception {
        mockMvc.perform(
                get("/events/user/"+event.getId())
        ).andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(event.getId()))
                .andExpect(jsonPath("$.eventName").value(event.getEventName()))
                .andExpect(jsonPath("$.numberOfSlots").value(event.getNumberOfSlots()));
    }

    @Test
    @WithMockUser(username = "artem", roles = "ADMIN")
    public void eventNameShouldBeUpdated() throws Exception {

        final String eventUpdateDto = """
                {
                           "id": 1,
                           "eventName": "event",
                           "eventStatus": "ACTIVE",
                           "numberOfSlots": 50,
                           "ageLimit": 12
                }
                """;
        mockMvc.perform(
                put("/events/user")
                        .principal(new Principal() {
                            @Override
                            public String getName() {
                                return "artem";
                            }
                        })
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventUpdateDto)
        ).andExpect(status().is2xxSuccessful());

        final Event smith = eventRepository.getById(1l);
        assertEquals(smith.getNumberOfSlots(), 50);
    }

    @Test
    @WithMockUser(username = "artem", roles = "ADMIN")
    public void shouldReturnErrorTextWhenEventNotExists() throws Exception {
        mockMvc.perform(
                get("/events/user/100")
        ).andExpect(status().isNotFound());
    }

    /*andExpect(content().json(
                        "{" +
                        "\"id\": 1,\n" +
                        "    \"eventName\": \"event\",\n" +
                        "    \"eventStatus\": \"COMPLITED\",\n" +
                        "    \"numberOfSlots\": 120,\n" +
                        "    \"availableSlots\": 120,\n" +
                        "    \"ageLimit\": 12,\n" +
                        "    \"startTime\": [\n" +
                        "        2021,\n" +
                        "        12,\n" +
                        "        29,\n" +
                        "        15,\n" +
                        "        21,\n" +
                        "        30,\n" +
                        "        135000000\n" +
                        "    ],\n" +
                        "    \"endTime\": [\n" +
                        "        2021,\n" +
                        "        12,\n" +
                        "        29,\n" +
                        "        15,\n" +
                        "        22,\n" +
                        "        30,\n" +
                        "        135000000\n" +
                        "    ],\n" +
                        "    \"placeId\": 1,\n" +
                        "    \"creatorId\": 1}"));*/
}
