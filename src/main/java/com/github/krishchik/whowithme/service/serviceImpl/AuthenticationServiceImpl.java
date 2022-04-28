package com.github.krishchik.whowithme.service.serviceImpl;

import com.github.krishchik.whowithme.service.AuthentificationService;
import com.github.krishchik.whowithme.service.UserService;
import com.github.krishchik.whowithme.controller.dto.AuthDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.model.Profile;
import com.github.krishchik.whowithme.model.User;
import com.github.krishchik.whowithme.repository.ProfileCrudRepository;
import com.github.krishchik.whowithme.repository.RoleCrudRepository;
import com.github.krishchik.whowithme.repository.UserCrudRepository;
import com.github.krishchik.whowithme.security.jwt.JwtTokenProvider;
import com.github.krishchik.whowithme.exception.OperationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthentificationService {

    private final UserCrudRepository userCrudRepository;
    private final ProfileCrudRepository profileCrudRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleCrudRepository roleCrudRepository;
    private final AuthenticationManager authenticate;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Override
    public ResponseEntity  login(UserDto userDto) {
        try {
            User user = userService.getUserByLogin(userDto.getLogin());
            authenticate.authenticate(new UsernamePasswordAuthenticationToken(userDto.getLogin(), userDto.getPassword()));
            String token = jwtTokenProvider.createToken(userDto.getLogin(), user.getRole());
            Map<Object, Object> response = new HashMap<>();
            response.put("username", userDto.getLogin());
            response.put("token", token);
            log.info("user with id "+ user.getId()+ "and login " + user.getLogin() + "login");
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("message", "Password is incorrect!");
            log.info("failed login attempt");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }

    @Override
    @Transactional
    public AuthDto registration(UserDto userDto) {
        if (loginExists(userDto.getLogin())) {
            throw new OperationException("There is an account with that login: "
                    + userDto.getLogin());
        }
        User user =new User();
        Profile profile = new Profile();
        profileCrudRepository.save(profile);
        user.setProfile(profile);
        user.setLogin(userDto.getLogin());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(roleCrudRepository.getById(2L));
        userCrudRepository.save(user);

        authenticate.authenticate(new UsernamePasswordAuthenticationToken(userDto.getLogin(), userDto.getPassword()));
        String token = jwtTokenProvider.createToken(userDto.getLogin(), user.getRole());

        AuthDto authDto = new AuthDto();
        authDto.setToken(token);
        authDto.setLogin(userDto.getLogin());
        log.info("new user with id "+ user.getId()+ "and login " + user.getLogin() + "registered");
        return authDto;
    }

    private boolean loginExists(String login) {
        return userCrudRepository.findUserByLogin(login).isPresent();
    }

}
