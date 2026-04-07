package com.employee.exceptions;


import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException{
    private final String message;
    @Getter
    private final HttpStatus status;

    public ResourceNotFoundException(String message){
        this.message = message;
        this.status = HttpStatus.NOT_FOUND;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
