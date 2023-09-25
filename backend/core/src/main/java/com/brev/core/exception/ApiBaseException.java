package com.brev.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class ApiBaseException extends RuntimeException {
    private HttpStatus status;
    private Map<String, Object> errors;

    public ApiBaseException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public ApiBaseException(String message, HttpStatus status, Map<String, Object> errors) {
        super(message);
        this.status = status;
        this.errors = errors;
    }

    public ApiErrorResponse toApiErrorResponse() {
        return new ApiErrorResponse(getMessage(), getErrors());
    }


}
