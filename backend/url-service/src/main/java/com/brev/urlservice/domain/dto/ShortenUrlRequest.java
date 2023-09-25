package com.brev.urlservice.domain.dto;

import com.brev.urlservice.domain.document.UrlDocument;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public record ShortenUrlRequest(@NotEmpty String originalUrl, @Length(min = 2, max = 20) String customShortUrl) {

    public UrlDocument toUrlDocument() {
        UrlDocument url = new UrlDocument();
        url.setOriginalUrl(originalUrl);
        return url;
    }
}
