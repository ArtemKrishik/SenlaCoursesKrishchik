package com.github.krishchik.whowithme.service;

import com.github.krishchik.whowithme.api.repository.UserRepository;
import com.github.krishchik.whowithme.api.service.UserService;
import com.github.krishchik.whowithme.controller.Mapper.Mapper;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    private final Mapper<UserDto, User> userMapper;

    @Override
    @Transactional
    public void createUser(UserDto userDto) throws Exception {
        userRepository.save(userMapper.toEntity(userDto, User.class));
    }

    @Override
    @Transactional
    public void updateUser(UserDto userDto) throws Exception {
        userRepository.update(userMapper.toEntity(userDto, User.class));
    }

    @Override
    @Transactional
    public UserDto getUserById(Long userId) throws Exception {
        return userMapper.toDto(userRepository.getById(userId), UserDto.class);
    }

    @Override
    @Transactional
    public void deleteUser(UserDto userDto) throws Exception {
        userRepository.delete(userRepository.getById(userMapper.toEntity(userDto, User.class).getId()));
    }

    @Override
    @Transactional
    public List<UserDto> getAllUsers() throws Exception {
        return userMapper.listToDto(userRepository.getAll(), User.class);
    }
}
