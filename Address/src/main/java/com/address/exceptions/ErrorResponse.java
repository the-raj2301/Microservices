package com.address.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private String message;
    private HttpStatus status;
    private LocalDateTime timestamp;

    public ErrorResponse(String message, HttpStatus status){
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
    
    
}
