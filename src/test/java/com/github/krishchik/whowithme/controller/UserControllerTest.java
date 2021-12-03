package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.WebApplicationTest;
import com.github.krishchik.whowithme.api.repository.UserRepository;
import com.github.krishchik.whowithme.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Transactional
public class UserControllerTest extends WebApplicationTest {

    @Autowired
    private UserRepository userRepository;

    private final User user = User.builder().id(1l).login("artem").password("password").build();

    @Test
    public void userShouldBeCreated() throws Exception {

        assertEquals(0, userRepository.getAll().size());


        final String userDto = """  
                        {
                           "id": 1,
                           "login": "artem",
                           "password": "password"
                        }
                """;
        mockMvc.perform(
                post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDto)
        ).andExpect(status().is2xxSuccessful());


        assertNotNull(userRepository.getById(1l));
    }

    @Test
    public void userShouldBeDeletedById() throws Exception {
        userRepository.save(user);

        final String userUpdateDto = String.format("""
                {
                           "id": 1,
                           "login": "artem",
                           "password": "password"
                }
                """);
        mockMvc.perform(
                delete("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUpdateDto)
        ).andExpect(status().is2xxSuccessful());

        final User user1 = userRepository.getById(user.getId());

        assertNull(user1);
    }

    @Test
    public void userShouldReturnWithCorrectFields() throws Exception {
        userRepository.save(user);

        mockMvc.perform(
                get("/users/" + user.getId())
        ).andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.login").value(user.getLogin()))
                .andExpect(jsonPath("$.password").value(user.getPassword()));
    }

    @Test
    public void userNameShouldBeUpdated() throws Exception {
        userRepository.save(user);

        final String userUpdateDto = String.format("""
                {
                           "id": 1,
                           "login": "andrei",
                           "password": "password"
                }
                """);

        mockMvc.perform(
                put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUpdateDto)
        ).andExpect(status().is2xxSuccessful());

        final User smith = userRepository.getById(1l);
        assertEquals(smith.getLogin(), "andrei");
    }

    @Test
    public void shouldReturnErrorTextWhenUserNotExists() throws Exception {
        mockMvc.perform(
                get("/users/12")
        ).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("user with id 12 wasn`t found"));
    }

}
