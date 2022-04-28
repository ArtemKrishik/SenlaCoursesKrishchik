package com.github.krishchik.whowithme.security;

import com.github.krishchik.whowithme.service.serviceApi.UserService;
import com.github.krishchik.whowithme.model.User;
import com.github.krishchik.whowithme.security.jwt.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final JwtUserFactory jwtUserFactory;

    @Autowired
    public JwtUserDetailsService(UserService userService, JwtUserFactory jwtUserFactory) {
        this.userService = userService;
        this.jwtUserFactory = jwtUserFactory;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userService.getUserByLogin(login);
        return jwtUserFactory.create(user);
    }
}
