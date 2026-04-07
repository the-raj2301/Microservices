package com.employee.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;


public class BadRequestException extends RuntimeException{
    private final String message;
    @Getter
    private final HttpStatus status;

    public BadRequestException(String message){
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
