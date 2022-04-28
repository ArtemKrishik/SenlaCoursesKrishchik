package com.github.krishchik.whowithme.config;

import com.github.krishchik.whowithme.repository.filter.EventSpecificationsBuilder;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@ComponentScan("com.github")
public class ApplicationConfiguration {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        modelMapper
                .getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        return modelMapper;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public EventSpecificationsBuilder eventSpecificationsBuilder() {
        return new EventSpecificationsBuilder();
    }
}
