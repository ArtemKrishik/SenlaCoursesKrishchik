package com.github.krishchik.whowithme.api.service;

import com.github.krishchik.whowithme.model.User;

import java.util.List;

public interface UserService {

    void createUser(User createdUser) throws Exception;

    void updateUser(User updatedUser) throws Exception;

    User getUserById(Long userId) throws Exception;

    void deleteUser(User deletedUser) throws Exception;

    List<User> getAllUsers() throws Exception;
}
