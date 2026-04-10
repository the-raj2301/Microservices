package com.employee.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException{

    private final HttpStatus status;

    public CustomException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public CustomException(String message){
        super(message);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
