package com.brev.urlservice.controller;

import com.brev.urlservice.domain.document.UrlDocument;
import com.brev.urlservice.domain.dto.PageableResponse;
import com.brev.urlservice.domain.dto.ShortenUrlRequest;
import com.brev.urlservice.domain.dto.UrlResponse;
import com.brev.urlservice.service.UrlDocumentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.brev.core.jwt.TokenInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/urls")
public class ApiController {

    private final UrlDocumentService urlDocumentService;

    private final ObjectMapper mapper;

    @Autowired
    public ApiController(UrlDocumentService urlDocumentService, ObjectMapper mapper) {
        this.urlDocumentService = urlDocumentService;
        this.mapper = mapper;
    }


    @PostMapping
    public UrlResponse shortenUrl(@Valid @RequestBody ShortenUrlRequest shortenUrlRequest,
                                  Authentication authentication, HttpServletRequest request) throws JsonProcessingException {
        int userId = ((TokenInfo) authentication.getPrincipal()).userId();
        UrlDocument url = urlDocumentService.createShortenUrl(shortenUrlRequest, userId);
        return UrlResponse.fromUrlDocument(url, request);
    }

    @GetMapping
    public PageableResponse<UrlResponse> getAllMyLinks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(1000) int size,
            Authentication authentication, HttpServletRequest request) {
        int userId = ((TokenInfo) authentication.getPrincipal()).userId();
        var urlResponsePage = urlDocumentService.findByUserId(userId, PageRequest.of(page, size))
                .map((url) -> UrlResponse.fromUrlDocument(url, request));
        return PageableResponse.fromPage(urlResponsePage);
    }
}
