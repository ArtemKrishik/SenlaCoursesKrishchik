package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.WebApplicationTest;
import com.github.krishchik.whowithme.model.Credential;
import com.github.krishchik.whowithme.repository.UserCrudRepository;
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
public class CredentialControllerTest extends WebApplicationTest {

    @Autowired
    private UserCrudRepository userRepository;

    private final Credential credential = Credential.builder().id(1l).login("artem").password("password").build();

    @Test
    public void userShouldReturnWithCorrectFields() throws Exception {

        mockMvc.perform(
                get("/users" + credential.getId())
        ).andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(credential.getId()))
                .andExpect(jsonPath("$.login").value(credential.getLogin()))
                .andExpect(jsonPath("$.password").value("$2a$04$/37BokSgKBfktDPXmDnye.FLXOqdFBQnSLmL/mEvHlCIl9dTOK6.S"));
    }

    @Test
    public void userNameShouldBeUpdated() throws Exception {
        userRepository.save(credential);

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

        final Credential smith = userRepository.getById(1l);
        assertEquals(smith.getLogin(), "andrei");
    }

    @Test
    public void shouldReturnErrorTextWhenUserNotExists() throws Exception {
        mockMvc.perform(
                get("/users/12")
        ).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("credential with id 12 wasn`t found"));
    }

}
