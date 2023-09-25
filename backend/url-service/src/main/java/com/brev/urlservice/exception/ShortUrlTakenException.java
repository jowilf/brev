package com.brev.urlservice.exception;

import com.brev.core.exception.ApiBaseException;
import org.springframework.http.HttpStatus;

public class ShortUrlTakenException extends ApiBaseException {

    public ShortUrlTakenException(String shortUrl) {
        super("The <%s> short URL has already been claimed by someone else. Please try a different one.".formatted(
                shortUrl), HttpStatus.CONFLICT);
    }

}
