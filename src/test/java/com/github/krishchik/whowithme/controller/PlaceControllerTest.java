package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.WebApplicationTest;
import com.github.krishchik.whowithme.api.repository.PlaceRepository;
import com.github.krishchik.whowithme.model.Place;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class PlaceControllerTest extends WebApplicationTest {

    @Autowired
    private PlaceRepository placeRepository;

    private final Place place = Place.builder().id(1l).price(10).capacity(10).build();

    @Test
    public void placeShouldBeCreated() throws Exception {

        assertEquals(0, placeRepository.getAll().size());


        final String placeDto = """  
                        {
                           "id": 1,
                           "price": 10,
                           "capacity": 10
                        }
                """;
        mockMvc.perform(
                post("/places")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(placeDto)
        ).andExpect(status().is2xxSuccessful());


        assertNotNull(placeRepository.getById(1l));
    }

    @Test
    public void placeShouldBeDeletedById() throws Exception {
        placeRepository.save(place);

        final String placeUpdateDto = String.format("""
                {
                           "id": 1,
                           "login": "artem",
                           "password": "password"
                }
                """);
        mockMvc.perform(
                delete("/places")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(placeUpdateDto)
        ).andExpect(status().is2xxSuccessful());

        final Place place1 = placeRepository.getById(place.getId());

        assertNull(place1);
    }

    @Test
    public void placeShouldReturnWithCorrectFields() throws Exception {
        placeRepository.save(place);

        mockMvc.perform(
                get("/places/" + place.getId())
        ).andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(place.getId()))
                .andExpect(jsonPath("$.capacity").value(place.getCapacity()))
                .andExpect(jsonPath("$.price").value(place.getPrice()));
    }

    @Test
    public void placeNameShouldBeUpdated() throws Exception {
        placeRepository.save(place);

        final String placeUpdateDto = String.format("""
                {
                           "id": 1,
                           "price": 20,
                           "capacity": 20
                }
                """);

        mockMvc.perform(
                put("/places")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(placeUpdateDto)
        ).andExpect(status().is2xxSuccessful());

        final Place smith = placeRepository.getById(1l);
        assertEquals(smith.getCapacity(), 20);
    }

    @Test
    public void shouldReturnErrorTextWhenUserNotExists() throws Exception {
        mockMvc.perform(
                get("/places/12")
        ).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("place with id 12 wasn`t found"));
    }
}
