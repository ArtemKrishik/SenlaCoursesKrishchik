package com.github.krishchik.whowithme.security;

import com.github.krishchik.whowithme.model.Credential;
import com.github.krishchik.whowithme.security.jwt.JwtUserFactory;
import com.github.krishchik.whowithme.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtCredentialDetailsService implements UserDetailsService {

    private final UserService userService;
    private final JwtUserFactory jwtUserFactory;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Credential user = userService.getUserByLogin(login);
        return jwtUserFactory.create(user);
    }

}
