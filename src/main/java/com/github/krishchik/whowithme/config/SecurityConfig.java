package com.github.krishchik.whowithme.config;

import com.github.krishchik.whowithme.security.jwt.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_ENDPOINT = "/login";
    private static final String ADMIN_ENDPOINT = "**/admin/**";
    private static final String USER_ENDPOINT = "**/user/**";

    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_USER = "USER";

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .mvcMatchers(LOGIN_ENDPOINT).permitAll()
                .mvcMatchers(ADMIN_ENDPOINT).hasRole(ROLE_ADMIN)
                .mvcMatchers(USER_ENDPOINT).hasAnyRole(ROLE_ADMIN, ROLE_USER)
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
