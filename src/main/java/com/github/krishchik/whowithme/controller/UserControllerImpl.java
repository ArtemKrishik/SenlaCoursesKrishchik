package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.controller.dto.ProfileDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserControllerImpl {

    @Autowired
    private final UserServiceImpl userService;

    @PostMapping(value = "/admin")
    public void createUser(@RequestBody UserDto userDto) throws Exception {
        userService.createUser(userDto);
    }

    @GetMapping(value = "/admin/{id}")
    public UserDto getUserById(@PathVariable Long id) throws Exception {
        return userService.getUserById(id);
    }

    @PutMapping(value = "/user")
    public void updateUser(@RequestBody UserDto userDto) throws Exception {
        userService.updateUser(userDto);
    }

    @DeleteMapping(value = "/user")
    public void deleteUser(@RequestBody UserDto userDto) throws Exception {
        userService.deleteUser(userDto);
    }
    @GetMapping(value = "/admin")
    public List<UserDto> getAll() throws Exception {
        return userService.getAllUsers();
    }

    @GetMapping(value = "user/{id}/profile")
    public ProfileDto getUsersProfile(@PathVariable Long id) {
        return userService.getUsersProfile(id);
    }
}
