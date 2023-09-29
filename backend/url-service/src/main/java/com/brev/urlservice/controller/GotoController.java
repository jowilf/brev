package com.brev.urlservice.controller;

import com.brev.urlservice.exception.UrlNotFoundException;
import com.brev.urlservice.service.MessagingService;
import com.brev.urlservice.service.UrlDocumentService;
import com.brev.core.domain.Metric;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
public class GotoController {

    private final UrlDocumentService urlDocumentService;
    private final MessagingService messagingService;

    @Autowired

    public GotoController(UrlDocumentService urlDocumentService, MessagingService messagingService) {
        this.urlDocumentService = urlDocumentService;
        this.messagingService = messagingService;
    }

    @GetMapping(value = "/{shortUrl}", name = "goTo")
    @PreAuthorize("permitAll()")
    public void goTo(@NotBlank @PathVariable String shortUrl, HttpServletRequest request,
                     HttpServletResponse httpServletResponse) throws IOException {
        String originalUrl = urlDocumentService.getOriginalUrl(shortUrl);
        if (originalUrl == null)
            throw new UrlNotFoundException();
        messagingService.send(new Metric(shortUrl, request.getRemoteAddr()));
        urlDocumentService.setLastVisited(shortUrl);
        httpServletResponse.sendRedirect(originalUrl);
    }
}
