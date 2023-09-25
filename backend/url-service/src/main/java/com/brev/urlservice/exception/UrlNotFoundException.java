package com.brev.urlservice.exception;

import com.brev.core.exception.ApiBaseException;
import org.springframework.http.HttpStatus;

public class UrlNotFoundException extends ApiBaseException {

    public UrlNotFoundException() {
        super("URL not found.", HttpStatus.NOT_FOUND);
    }

}
