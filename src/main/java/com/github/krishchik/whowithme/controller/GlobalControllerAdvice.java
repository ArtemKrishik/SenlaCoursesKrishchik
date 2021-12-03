package com.github.krishchik.whowithme.controller;

import com.github.krishchik.whowithme.controller.dto.ErrorMessageDto;
import com.github.krishchik.whowithme.service.exception.DatabaseAccessException;
import com.github.krishchik.whowithme.service.exception.OperationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OperationException.class)
    public ErrorMessageDto operationExceptionHandler(OperationException operationException) {
        return new ErrorMessageDto(operationException.getMessage());
   }

   @ExceptionHandler(DatabaseAccessException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageDto databaseAccessExceptionHendler(DatabaseAccessException databaseAccessexception) {
        return new ErrorMessageDto(databaseAccessexception.getMessage());
   }
}
