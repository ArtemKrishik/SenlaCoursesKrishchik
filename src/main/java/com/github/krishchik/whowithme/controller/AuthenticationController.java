package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.service.AuthentificationService;
import com.github.krishchik.whowithme.controller.dto.AuthDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final AuthentificationService authentificationService;

    @PostMapping("/login")
    @PreAuthorize("permitAll")
    public ResponseEntity login(@RequestBody UserDto requestDto) {
        return authentificationService.login(requestDto);
    }

    @PostMapping("/registration")
    @PreAuthorize("permitAll")
    public AuthDto registration(@RequestBody UserDto requestDto) {
       return authentificationService.registration(requestDto);
    }

}

