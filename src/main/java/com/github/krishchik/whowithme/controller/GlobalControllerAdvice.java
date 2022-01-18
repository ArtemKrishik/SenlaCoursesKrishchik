package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.controller.dto.MessageDto;
import com.github.krishchik.whowithme.service.exception.DatabaseAccessException;
import com.github.krishchik.whowithme.service.exception.JwtTokenException;
import com.github.krishchik.whowithme.service.exception.OperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OperationException.class)
    public MessageDto notFoundExceptionHandler(OperationException operationException) {
        return new MessageDto(operationException.getMessage());
   }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public MessageDto globalExceptionHandler(Exception ex) {
        MessageDto message = new MessageDto(ex.getMessage());
        return message;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public MessageDto handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        MessageDto message = new MessageDto(
                ex.getFieldError().getField() + " : " + ex.getFieldError().getDefaultMessage()
                );
        return message;
    }


    @ExceptionHandler(DatabaseAccessException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public MessageDto databaseAccessExceptionHandler(DatabaseAccessException databaseAccessexception) {
        return new MessageDto(databaseAccessexception.getMessage());
   }



}
