package com.address.exceptions;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class BadRequestException extends RuntimeException{
//    private final String message;
    private final HttpStatus status;

    public BadRequestException(String message){
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }



}
