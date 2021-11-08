package com.github.krishchik.whowithme.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.krishchik.whowithme.model.User;
import com.github.krishchik.whowithme.service.UserServiceImpl;
import com.github.krishchik.whowithme.util.JsonMapper;
import org.springframework.stereotype.Component;

@Component
public class UserControllerImpl {

    private final UserServiceImpl userService;
    private final JsonMapper jsonMapper;

    public UserControllerImpl(UserServiceImpl userService,JsonMapper jsonMapper) {
        this.userService = userService;
        this.jsonMapper = jsonMapper;
    }

    public void createUser(String userJsonString) throws JsonProcessingException {
        User user = jsonMapper.convertToUser(userJsonString);
        userService.createUser(user);
    }

    public String getUserById(Long userId) throws JsonProcessingException {
        User user = userService.getUserById(userId);
       return jsonMapper.convertUserTOJson(user);

    }

    public void updateUser(String userJsonString) throws JsonProcessingException {
        User user = jsonMapper.convertToUser(userJsonString);
        userService.updateUser(user);
    }

    public void deleteUser(String userJsonString) throws JsonProcessingException {
        User user = jsonMapper.convertToUser(userJsonString);
        userService.deleteUser(user);
    }

    public String getAll() throws JsonProcessingException {
        return jsonMapper.convertUserTOJson(userService.getAllUsers());
    }


}
