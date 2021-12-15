package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.api.service.UserService;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.model.User;
import com.github.krishchik.whowithme.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserDto requestDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getLogin(), requestDto.getPassword()));
            User user = userService.getUserByLogin(requestDto.getLogin());

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + requestDto.getLogin() + " not found");
            }

            String token = jwtTokenProvider.createToken(requestDto.getLogin(), user.getRole());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", requestDto.getLogin());
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("message", "Login or password is incorrect!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }
}

