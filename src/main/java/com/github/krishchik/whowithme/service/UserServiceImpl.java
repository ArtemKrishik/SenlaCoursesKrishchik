package com.github.krishchik.whowithme.service;

import com.github.krishchik.whowithme.api.repository.ProfileRepository;
import com.github.krishchik.whowithme.api.repository.UserRepository;
import com.github.krishchik.whowithme.api.service.UserService;
import com.github.krishchik.whowithme.controller.Mapper.Mapper;
import com.github.krishchik.whowithme.controller.dto.ProfileDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.model.User;
import com.github.krishchik.whowithme.service.converter.UserConverter;
import com.github.krishchik.whowithme.service.exception.OperationException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ProfileRepository profileRepository;
    private final UserConverter userConverter;

    @Override
    @Transactional
    public void createUser(UserDto userDto) throws Exception {
        userRepository.save(userConverter.toEntity(userDto));
    }

    @Override
    @Transactional
    public void updateUser(UserDto userDto) throws Exception {
        User userToChange = userRepository.getById(userDto.getId());
        if(userToChange == null) throw new OperationException("Incorrect input when tryin change user");
        userToChange.setLogin(userDto.getLogin());
        userToChange.setPassword(userDto.getPassword());
        userRepository.update(userToChange);
    }

    @Override
    @Transactional
    public UserDto getUserById(Long userId) throws Exception {
        User user = userRepository.getById(userId);
        if(user == null) throw new OperationException("user with id "+userId+" wasn`t found");
        return userConverter.toDto(user);
    }



    @Override
    @Transactional
    public void deleteUser(UserDto userDto) throws Exception {
        userRepository.delete(userConverter.toEntity(userDto));
    }

    @Override
    @Transactional
    public List<UserDto> getAllUsers() throws Exception {
        return userConverter.listToDto(userRepository.getAll());
    }

    @Override
    @Transactional
    public ProfileDto getUsersProfile(Long userId) {
        return userConverter.toDto(userRepository.getUsersProfile(userId));
    }

    @Override
    @Transactional
    public User getUserByLogin(String login) {
        return userRepository.getUserByLogin(login);
    }

}
