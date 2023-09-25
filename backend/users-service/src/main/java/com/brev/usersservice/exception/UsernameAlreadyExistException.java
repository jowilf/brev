package com.brev.usersservice.exception;

import com.brev.core.exception.ApiBaseException;
import org.springframework.http.HttpStatus;

public class UsernameAlreadyExistException extends ApiBaseException {
    public UsernameAlreadyExistException() {
        super("This username already exist", HttpStatus.CONFLICT);
    }
}
