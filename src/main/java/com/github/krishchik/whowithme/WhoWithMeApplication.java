package com.github.krishchik.whowithme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class WhoWithMeApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhoWithMeApplication.class, args);
    }

}
