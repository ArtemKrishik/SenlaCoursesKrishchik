package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.service.serviceApi.AuthentificationService;
import com.github.krishchik.whowithme.controller.dto.AuthDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthentificationService authentificationService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserDto requestDto) {
        return authentificationService.login(requestDto);
    }

    @PostMapping("/registration")
    public AuthDto registration(@RequestBody UserDto requestDto) {
       return authentificationService.registration(requestDto);
    }

}

