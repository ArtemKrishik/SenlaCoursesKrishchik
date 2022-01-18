package com.github.krishchik.whowithme.service.serviceApi;

import com.github.krishchik.whowithme.controller.dto.AuthDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface AuthentificationService {


    ResponseEntity login(UserDto userDto);

    AuthDto registration(UserDto userDto);

}
