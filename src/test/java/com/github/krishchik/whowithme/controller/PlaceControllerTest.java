package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.WebApplicationTest;

import com.github.krishchik.whowithme.model.Place;
import com.github.krishchik.whowithme.repository.repositoryApi.PlaceCrudRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class PlaceControllerTest extends WebApplicationTest {

    @Autowired
    private PlaceCrudRepository placeRepository;

    private final Place place = Place.builder().id(1l).price(100).capacity(10).build();

    @Test
    public void placeShouldBeCreated() throws Exception {

        assertEquals(1, placeRepository.findAll().size());


        final String placeDto = """  
                        {
                           "placeName": "name",
                           "price": 10,
                           "capacity": 10
                        }
                """;
        mockMvc.perform(
                post("/places/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(placeDto)
        ).andExpect(status().is2xxSuccessful());


        assertEquals(2, placeRepository.findAll().size());
    }

    @Test
    public void placeShouldBeDeletedById() throws Exception {

        mockMvc.perform(
                delete("/places/admin/1")
        ).andExpect(status().is2xxSuccessful());
        assertThrows(
                JpaObjectRetrievalFailureException.class,
                () -> placeRepository.getById(place.getId())
        );
    }

    @Test
    public void placeShouldReturnWithCorrectFields() throws Exception {

        mockMvc.perform(
                get("/places/user/" + place.getId())
        ).andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(place.getId()))
                .andExpect(jsonPath("$.capacity").value(place.getCapacity()))
                .andExpect(jsonPath("$.price").value(place.getPrice()));
    }

    @Test
    public void placeNameShouldBeUpdated() throws Exception {

        final String placeUpdateDto = String.format("""
                {
                           "id": 1,
                           "price": 20,
                           "capacity": 20
                }
                """);

        mockMvc.perform(
                put("/places/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(placeUpdateDto)
        ).andExpect(status().is2xxSuccessful());

        final Place smith = placeRepository.getById(1l);
        assertEquals(smith.getCapacity(), 20);
    }

    @Test
    public void shouldReturnErrorTextWhenPlaceNotExists() throws Exception {
        mockMvc.perform(
                get("/places/user/12")
        ).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("place with id 12 wasn`t found"));
    }
}
