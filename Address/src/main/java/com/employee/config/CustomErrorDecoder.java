package com.employee.config;

import com.employee.exceptions.CustomException;
import com.employee.exceptions.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.io.InputStream;

public class CustomErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    @Override
    public Exception decode(String s, Response response) {
        try (InputStream inputStream = response.body().asInputStream();) {
            ErrorResponse errorResponse = objectMapper.readValue(inputStream, ErrorResponse.class);
            return new CustomException(errorResponse.getStatus(), errorResponse.getMessage());
        } catch (IOException e) {
            throw new CustomException("INTERNAL_SERVER_ERROR");
        }
    }
}
