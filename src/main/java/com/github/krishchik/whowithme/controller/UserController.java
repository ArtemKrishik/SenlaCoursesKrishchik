package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.controller.dto.MessageDto;
import com.github.krishchik.whowithme.controller.dto.ProfileDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.service.serviceImpl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping(value = "/admin")
    public void createUser(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
    }

    @GetMapping(value = "/admin/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping(value = "/user")
    public void updateUser(@RequestBody UserDto userDto) {
        userService.updateUser(userDto);
    }

    @DeleteMapping(value = "/user/{id}")
    public void deleteUser(
            @RequestBody Long id,
            Principal principal
    ) {
        userService.deleteUser(id);
    }

    @GetMapping(value = "/admin")
    public Page<UserDto> getAll(
            @RequestParam(value = "size", required = false, defaultValue = "3") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page
    ) {

        return userService.getAllUsers(PageRequest.of(page, size));
    }

    @PutMapping(value = "/user/profile")
    public void updateProfile(
            @RequestBody ProfileDto profileDto,
            Principal principal
    ) {
        userService.updateProfile(profileDto, principal);
    }

    @GetMapping(value = "user/profile")
    public ProfileDto getUsersProfile(Principal principal) {

        return userService.getUsersProfile(principal);
    }

    @PutMapping(value = "user/subscribeOnEvent")
    public MessageDto subscribeUserOnEvent(
            Principal principal,
            @PathVariable Long eventId
            ) {
        return userService.subscribeUserOnEvent(principal, eventId);
    }

    @PutMapping(value = "user/unsubscribeFromEvent")
    public MessageDto unsubscribeUserFromEvent(
            @PathVariable Long eventId,
            Principal principal
    ) {
        return userService.unsubscribeUserFromEvent(principal, eventId);
    }
}
