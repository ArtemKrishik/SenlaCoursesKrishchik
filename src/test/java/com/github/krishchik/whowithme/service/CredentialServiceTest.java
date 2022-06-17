package com.github.krishchik.whowithme.service;

import com.github.krishchik.whowithme.controller.dto.ProfileDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.model.Credential;
import com.github.krishchik.whowithme.model.Profile;
import com.github.krishchik.whowithme.model.Role;
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
public class CredentialServiceTest {

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
    private final Credential credential = Credential.builder().id(1l).login("login").password("password").build();

    @Test
    public void getById()  {
        when(userRepository.findById(any())).thenReturn(Optional.of(credential));
        when(userConverter.toDto(any(Credential.class))).thenReturn(userDto);
        final UserDto userDto = userService.getUserById(1l);
        assertEquals(1l,userDto.getId());
    }

    @Test
    public void deleteUser() {
        doNothing().when(userRepository).delete(credential);
        when(userRepository.findById(any())).thenReturn(Optional.of(credential));
        userService.deleteUser(1l);
        verify(userRepository, times(1)).delete(credential);
    }

    @Test
    public void updateUser() {
        when(userRepository.findById(any())).thenReturn(Optional.of(credential));
        when(userRepository.save(credential)).thenReturn(credential);
        userService.updateUser(userDto);
        verify(userRepository, times(1)).save(credential);
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
        List<Credential> credentials = new ArrayList<>();
        credentials.add(credential);
        Page<Credential> pageOfUsers = new PageImpl<>(credentials, pageable, credentials.size());
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
        when(userRepository.findCredentialByLogin(principal.getName())).thenReturn(Optional.of(credential));
        when(profileRepository.findProfileByCredentialId(any())).thenReturn(Optional.of(profile));
        when(profileConverter.toDto(any(Profile.class))).thenReturn(profileDto);

        assertEquals(profileDto, userService.getUsersProfile(principal));
    }


}
