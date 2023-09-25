package com.brev.usersservice.exception;

import com.brev.core.exception.ApiBaseException;
import org.springframework.http.HttpStatus;

public class LoginException extends ApiBaseException {
    public LoginException() {
        super("Invalid username or password", HttpStatus.NOT_FOUND);
    }
}

