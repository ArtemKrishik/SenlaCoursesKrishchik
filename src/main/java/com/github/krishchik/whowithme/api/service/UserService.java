package com.github.krishchik.whowithme.api.service;

import com.github.krishchik.whowithme.controller.dto.UserDto;

import java.util.List;

public interface UserService {

    void createUser(UserDto createdUser) throws Exception;

    void updateUser(UserDto updatedUser) throws Exception;

    UserDto getUserById(Long userId) throws Exception;

    void deleteUser(UserDto deletedUser) throws Exception;

    List<UserDto> getAllUsers() throws Exception;
}
