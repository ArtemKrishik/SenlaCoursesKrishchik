package com.github.krishchik.whowithme.service;

import com.github.krishchik.whowithme.controller.dto.MessageDto;
import com.github.krishchik.whowithme.controller.dto.ProfileDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.model.User;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface UserService {

    void createUser(UserDto createdUser);

    void updateUser(UserDto updatedUser);

    UserDto getUserById(Long userId);

    void deleteUser(Long userId);

    Page<UserDto> getAllUsers(Pageable pageable);

    ProfileDto getUsersProfile(Principal principal);

    User getUserByLogin(String login);

    MessageDto subscribeUserOnEvent(Principal principal, Long eventId);

    MessageDto unsubscribeUserFromEvent(Principal principal, Long eventId);

    void updateProfile(ProfileDto profileDto, Principal principal);


}
