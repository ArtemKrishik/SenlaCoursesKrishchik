package com.github.krishchik.whowithme.security.jwt;

import com.github.krishchik.whowithme.model.Role;
import com.github.krishchik.whowithme.model.Credential;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public User create(Credential credential) {
        return new User(
                credential.getLogin(),
                credential.getPassword(),
                mapToGrantedAuthorities(credential.getRole()));
    }

    private List<GrantedAuthority> mapToGrantedAuthorities(Role userRole) {
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}

