package com.github.krishchik.whowithme.service;

import com.github.krishchik.whowithme.api.repository.UserRepository;
import com.github.krishchik.whowithme.api.service.UserService;
import com.github.krishchik.whowithme.controller.dto.ProfileDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.model.Profile;
import com.github.krishchik.whowithme.model.User;
import com.github.krishchik.whowithme.service.converter.UserConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserConverter userConverter;

    @Mock
    private UserRepository userRepository;

    private final UserDto userDto = UserDto.builder().id(1l).login("login").password("password").build();

    private final User user = User.builder().id(1l).login("login").password("password").build();

    @Test
    public void getById() throws Exception {
        when(userRepository.getById(any())).thenReturn(user);
        when(userConverter.toDto(any(User.class))).thenReturn(userDto);
        final UserDto userDto = userService.getUserById(1l);
        assertEquals(1l,userDto.getId());
    }

    @Test
    public void deleteUser() throws Exception {
        doNothing().when(userRepository).delete(user);
        when(userConverter.toEntity(any(UserDto.class))).thenReturn(user);
        userService.deleteUser(userDto);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void updateUser() throws Exception {
        when(userRepository.getById(any())).thenReturn(user);
        doNothing().when(userRepository).update(user);
        userService.updateUser(userDto);
        verify(userRepository, times(1)).update(user);
    }

    @Test
    public void saveUser() throws Exception {
        doNothing().when(userRepository).save(user);
        when(userConverter.toEntity(any(UserDto.class))).thenReturn(user);

        userService.createUser(userDto);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void getAll() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(user);
        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(userDto);
        when(userRepository.getAll()).thenReturn(users);
        when(userConverter.listToDto(anyList())).thenReturn(userDtos);
        final List<UserDto> userDtos1 = userService.getAllUsers();
        assertEquals(1, userDtos1.size());
    }

    @Test
    public void getUsersProfile() {
        Profile profile = new Profile();
        ProfileDto profileDto = new ProfileDto();
        when(userRepository.getUsersProfile(any())).thenReturn(profile);
        when(userConverter.toDto(any(Profile.class))).thenReturn(profileDto);
        assertEquals(profileDto, userService.getUsersProfile(1l));
    }


}
