package com.github.krishchik.whowithme.service.exception;

public class JwtTokenException extends RuntimeException{

    public JwtTokenException(String message) {
        super(message);
    }

}
