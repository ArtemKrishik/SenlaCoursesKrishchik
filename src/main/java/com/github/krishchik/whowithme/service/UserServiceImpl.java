package com.github.krishchik.whowithme.service;

import com.github.krishchik.whowithme.annotation.Transactional;
import com.github.krishchik.whowithme.api.repository.UserRepository;
import com.github.krishchik.whowithme.api.service.UserService;
import com.github.krishchik.whowithme.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void createUser(User createdUser) throws Exception {
        userRepository.save(createdUser);
        throw new RuntimeException();

    }

    @Override
    @Transactional
    public void updateUser(User updatedUser) throws Exception {
        userRepository.update(updatedUser);
    }

    @Override
    @Transactional
    public User getUserById(Long userId) throws Exception {
        return userRepository.getById(userId);
    }


    @Override
    @Transactional
    public void deleteUser(User deletedUser) throws Exception {
        userRepository.delete(deletedUser.getId());

    }

    @Override
    @Transactional
    public List<User> getAllUsers() throws Exception {
        return userRepository.getAll();
    }
}
