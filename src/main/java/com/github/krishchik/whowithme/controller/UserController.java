package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.controller.dto.MessageDto;
import com.github.krishchik.whowithme.controller.dto.ProfileDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.metamodel.Roles;
import com.github.krishchik.whowithme.service.serviceImpl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping
    @Secured(Roles.ADMIN)
    public void createUser(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
    }

    @GetMapping(value = "/{id}")
    @Secured(Roles.ADMIN)
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping(value = "/user")
    public void updateUser(@RequestBody UserDto userDto) {
        userService.updateUser(userDto);
    }

    @DeleteMapping(value = "/{id}")
    @Secured(Roles.ADMIN)
    public void deleteUser(
            @RequestBody Long id,
            Principal principal
    ) {
        userService.deleteUser(id);
    }

    @GetMapping
    @Secured(Roles.ADMIN)
    public Page<UserDto> getAll(
            @RequestParam(value = "size", required = false, defaultValue = "3") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page
    ) {

        return userService.getAllUsers(PageRequest.of(page, size));
    }

    @PutMapping(value = "/profile")
    @Secured({Roles.ADMIN, Roles.USER})
    public void updateProfile(
            @RequestBody ProfileDto profileDto,
            Principal principal
    ) {
        userService.updateProfile(profileDto, principal);
    }

    @GetMapping(value = "profile")
    @Secured({Roles.ADMIN, Roles.USER})
    public ProfileDto getUsersProfile(Principal principal) {

        return userService.getUsersProfile(principal);
    }

    @PutMapping(value = "subscribeOnEvent/{id}")
    @Secured({Roles.ADMIN, Roles.USER})
    public MessageDto subscribeUserOnEvent(
            Principal principal,
            @PathVariable Long id
            ) {
        return userService.subscribeUserOnEvent(principal, id);
    }

    @PutMapping(value = "unsubscribeFromEvent/{id}")
    @Secured({Roles.ADMIN, Roles.USER})
    public MessageDto unsubscribeUserFromEvent(
            @PathVariable Long id,
            Principal principal
    ) {
        return userService.unsubscribeUserFromEvent(principal, id);
    }
}
