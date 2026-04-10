    package com.address.exceptions;

    import lombok.Data;
    import lombok.Getter;
    import org.springframework.http.HttpStatus;

    @Getter
    public class MissingParameterException extends RuntimeException {
        private final HttpStatus status;

        public MissingParameterException(String message){
            super(message);
            this.status = HttpStatus.BAD_REQUEST;
        }

    }
