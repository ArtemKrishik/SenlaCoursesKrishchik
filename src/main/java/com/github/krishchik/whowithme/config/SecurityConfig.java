package com.github.krishchik.whowithme.config;

import com.github.krishchik.whowithme.security.jwt.JwtTokenFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_ENDPOINT = "/login";
    private static final String REGISTRATION_ENDPOINT = "/registration";

    private static final String ADMIN_ENDPOINT = "**/admin/**";
    private static final String USER_ENDPOINT = "**/user";

    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_USER = "USER";

    private final JwtTokenFilter jwtTokenFilter;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, LOGIN_ENDPOINT).permitAll()
                .antMatchers(HttpMethod.POST, REGISTRATION_ENDPOINT).permitAll()
                .antMatchers(HttpMethod.GET, "/events").permitAll()
                .anyRequest().authenticated();

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
