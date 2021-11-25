package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.controller.dto.PlaceDto;
import com.github.krishchik.whowithme.controller.dto.ProfileDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.model.User;
import com.github.krishchik.whowithme.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UserControllerImpl {

    @Autowired
    private final UserServiceImpl userService;


    public void createUser(UserDto userDto) throws Exception {
        userService.createUser(userDto);
    }

    public UserDto getUserById(Long userId) throws Exception {
        return userService.getUserById(userId);
    }

    public void updateUser(UserDto userDto) throws Exception {
        userService.updateUser(userDto);
    }

    public void deleteUser(UserDto userDto) throws Exception {
        userService.deleteUser(userDto);
    }

    public List<UserDto> getAll() throws Exception {
        return userService.getAllUsers();
    }

    public ProfileDto getUsersProfile(Long userId) {
        return userService.getUsersProfile(userId);
    }
}
