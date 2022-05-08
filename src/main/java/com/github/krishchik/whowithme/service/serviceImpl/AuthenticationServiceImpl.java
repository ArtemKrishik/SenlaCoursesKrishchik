package com.github.krishchik.whowithme.service.serviceImpl;

import com.github.krishchik.whowithme.security.jwt.JwtTokenProvider;
import com.github.krishchik.whowithme.service.AuthentificationService;
import com.github.krishchik.whowithme.service.UserService;
import com.github.krishchik.whowithme.controller.dto.AuthDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.model.Profile;
import com.github.krishchik.whowithme.model.Credential;
import com.github.krishchik.whowithme.repository.ProfileCrudRepository;
import com.github.krishchik.whowithme.repository.RoleCrudRepository;
import com.github.krishchik.whowithme.repository.UserCrudRepository;
//import com.github.krishchik.whowithme.security.jwt.JwtTokenProvider;
//import com.github.krishchik.whowithme.exception.OperationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthentificationService {


    @Override
    public AuthDto registration(UserDto userDto) {
        return null;
    }

    private final UserCrudRepository userCrudRepository;
    private final ProfileCrudRepository profileCrudRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleCrudRepository roleCrudRepository;
    private final AuthenticationManager authenticate;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public AuthenticationServiceImpl(UserCrudRepository userCrudRepository, ProfileCrudRepository profileCrudRepository, BCryptPasswordEncoder passwordEncoder, RoleCrudRepository roleCrudRepository, AuthenticationManager authenticate, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.userCrudRepository = userCrudRepository;
        this.profileCrudRepository = profileCrudRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleCrudRepository = roleCrudRepository;
        this.authenticate = authenticate;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Override
    public ResponseEntity  login(UserDto userDto) {
        try {
            Credential credential = userService.getUserByLogin(userDto.getLogin());
            authenticate.authenticate(new UsernamePasswordAuthenticationToken(userDto.getLogin(), userDto.getPassword()));
            String token = jwtTokenProvider.createToken(userDto.getLogin(), credential.getRole());
            Map<Object, Object> response = new HashMap<>();
            response.put("username", userDto.getLogin());
            response.put("token", token);
            log.info("credential with id "+ credential.getId()+ "and login " + credential.getLogin() + "login");
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("message", "Password is incorrect!");
            log.info("failed login attempt");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }

    /*@Override
    @Transactional
    public AuthDto registration(UserDto userDto) {
        if (loginExists(userDto.getLogin())) {
            throw new OperationException("There is an account with that login: "
                    + userDto.getLogin());
        }
        Credential credential =new Credential();
        Profile profile = new Profile();
        profileCrudRepository.save(profile);
        credential.setProfile(profile);
        credential.setLogin(userDto.getLogin());
        credential.setPassword(passwordEncoder.encode(userDto.getPassword()));
        credential.setRole(roleCrudRepository.getById(2L));
        userCrudRepository.save(credential);

        authenticate.authenticate(new UsernamePasswordAuthenticationToken(userDto.getLogin(), userDto.getPassword()));
        String token = jwtTokenProvider.createToken(userDto.getLogin(), credential.getRole());

        AuthDto authDto = new AuthDto();
        authDto.setToken(token);
        authDto.setLogin(userDto.getLogin());
        log.info("new credential with id "+ credential.getId()+ "and login " + credential.getLogin() + "registered");
        return authDto;
    }

    private boolean loginExists(String login) {
        return userCrudRepository.findUserByLogin(login).isPresent();
    }*/

}
