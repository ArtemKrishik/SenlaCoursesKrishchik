package com.github.krishchik.whowithme.api.service;

import com.github.krishchik.whowithme.model.User;

import java.util.List;

public interface UserService {

    void createUser(User createdUser);

    void updateUser(User updatedUser);

    User getUserById(Long userId);

    void deleteUser(User deletedUser);

    List<User> getAllUsers();
}
