package com.github.krishchik.whowithme.service;

import com.github.krishchik.whowithme.api.repository.UserRepository;
import com.github.krishchik.whowithme.api.service.UserService;
import com.github.krishchik.whowithme.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User createdUser) {
        userRepository.save(createdUser);
    }

    @Override
    public void updateUser(User updatedUser) {
        userRepository.update(updatedUser);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.getById(userId);
    }

    @Override
    public void deleteUser(User deletedUser) {
        userRepository.delete(deletedUser);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAll();
    }
}
