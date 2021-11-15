package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.model.User;
import com.github.krishchik.whowithme.service.UserServiceImpl;
import com.github.krishchik.whowithme.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserControllerImpl {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private  JsonMapper jsonMapper;


    public void createUser(String userJsonString) throws Exception {
        User user = jsonMapper.convertToUser(userJsonString);
        userService.createUser(user);
    }

    public String getUserById(Long userId) throws Exception {
        User user = userService.getUserById(userId);
       return jsonMapper.convertUserTOJson(user);

    }

    public void updateUser(String userJsonString) throws Exception {
        User user = jsonMapper.convertToUser(userJsonString);
        userService.updateUser(user);
    }

    public void deleteUser(String userJsonString) throws Exception {
        User user = jsonMapper.convertToUser(userJsonString);
        userService.deleteUser(user);
    }

    public String getAll() throws Exception {
        return jsonMapper.convertUserTOJson(userService.getAllUsers());
    }


}
