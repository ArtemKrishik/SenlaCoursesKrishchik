package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.WebApplicationTest;
import com.github.krishchik.whowithme.model.User;
import com.github.krishchik.whowithme.repository.repositoryApi.UserCrudRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Transactional
public class UserControllerTest extends WebApplicationTest {

    @Autowired
    private UserCrudRepository userRepository;

    private final User user = User.builder().id(1l).login("artem").password("password").build();

    @Test
    public void userShouldReturnWithCorrectFields() throws Exception {

        mockMvc.perform(
                get("/users/admin/" + user.getId())
        ).andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.login").value(user.getLogin()))
                .andExpect(jsonPath("$.password").value("$2a$04$/37BokSgKBfktDPXmDnye.FLXOqdFBQnSLmL/mEvHlCIl9dTOK6.S"));
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
                put("/users/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUpdateDto)
        ).andExpect(status().is2xxSuccessful());

        final User smith = userRepository.getById(1l);
        assertEquals(smith.getLogin(), "andrei");
    }

    @Test
    public void shouldReturnErrorTextWhenUserNotExists() throws Exception {
        mockMvc.perform(
                get("/users/admin/12")
        ).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("user with id 12 wasn`t found"));
    }

}
