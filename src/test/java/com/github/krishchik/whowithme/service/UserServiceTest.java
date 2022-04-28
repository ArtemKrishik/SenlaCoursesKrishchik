package com.github.krishchik.whowithme.service;

import com.github.krishchik.whowithme.controller.dto.ProfileDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.model.Profile;
import com.github.krishchik.whowithme.model.Role;
import com.github.krishchik.whowithme.model.User;
import com.github.krishchik.whowithme.repository.ProfileCrudRepository;
import com.github.krishchik.whowithme.repository.RoleCrudRepository;
import com.github.krishchik.whowithme.repository.UserCrudRepository;
import com.github.krishchik.whowithme.service.converter.ProfileConverter;
import com.github.krishchik.whowithme.service.converter.UserConverter;
import com.github.krishchik.whowithme.exception.OperationException;
import com.github.krishchik.whowithme.service.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserConverter userConverter;

    @Mock
    private ProfileConverter profileConverter;

    @Mock
    private UserCrudRepository userRepository;

    @Mock
    private ProfileCrudRepository profileRepository;

    @Mock
    private RoleCrudRepository roleRepository;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    private final UserDto userDto = UserDto.builder().id(1l).login("login").password("password").build();
    private final Role role = Role.builder().id(1l).name("ADMIN").build();
    private final User user = User.builder().id(1l).login("login").password("password").build();

    @Test
    public void getById()  {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(userConverter.toDto(any(User.class))).thenReturn(userDto);
        final UserDto userDto = userService.getUserById(1l);
        assertEquals(1l,userDto.getId());
    }

    @Test
    public void deleteUser() {
        doNothing().when(userRepository).delete(user);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        userService.deleteUser(1l);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void updateUser() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        userService.updateUser(userDto);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void updatePlaceReturnOperationException() throws Exception {
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        assertThrows(
                OperationException.class,
                () -> userService.updateUser(userDto)
        );
    }

    @Test
    public void deletePlaceReturnOperationException() throws Exception {
        when(userRepository.findById(5l)).thenReturn(Optional.ofNullable(null));
        assertThrows(
                OperationException.class,
                () -> userService.deleteUser(5l)
        );
    }

    @Test
    public void getByIdReturnOperationException() throws Exception {
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        assertThrows(
                OperationException.class,
                () -> userService.getUserById(5l)
        );
    }




    @Test
    public void getAll() throws Exception {
        Pageable pageable = PageRequest.of(0, 2, Sort.by("id"));
        List<User> users = new ArrayList<>();
        users.add(user);
        Page<User> pageOfUsers = new PageImpl<>(users, pageable, users.size());
        when(userRepository.findAll(pageable)).thenReturn(pageOfUsers);
        when(userConverter.toDto(any())).thenReturn(userDto);
        final Page<UserDto> userDtos1 = userService.getAllUsers(pageable);
        assertEquals(1, userDtos1.getTotalElements());
        assertEquals(1, userDtos1.getTotalPages());
    }

    @Test
    public void getUsersProfile() {
        Profile profile = new Profile();
        ProfileDto profileDto = new ProfileDto();
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "login";
            }
        };
        when(userRepository.findUserByLogin(principal.getName())).thenReturn(Optional.of(user));
        when(profileRepository.findProfileByUserId(any())).thenReturn(Optional.of(profile));
        when(profileConverter.toDto(any(Profile.class))).thenReturn(profileDto);

        assertEquals(profileDto, userService.getUsersProfile(principal));
    }


}
