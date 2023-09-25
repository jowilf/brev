package com.brev.urlservice.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

public class Utils {
    /**
     * Generate an absolute URL for a short URL based on the current request's host and scheme.
     *
     * @param request  The HttpServletRequest representing the current request.
     * @param shortUrl The short URL for which to generate the absolute URL.
     * @return The full absolute URL for the given short URL.
     */
    public static String generateAbsoluteShortUrl(HttpServletRequest request, String shortUrl) {
        // Get the current request's host and scheme
        String currentHost = request.getHeader("host");
        String scheme = request.getScheme();

        // Build the URL path for the redirection route based on the short URL
        String redirectUrlPath = MvcUriComponentsBuilder.fromMappingName("goTo").arg(0, shortUrl).build();

        // Combine the scheme, host, and URL path to create the full absolute URL
        return scheme + "://" + currentHost + redirectUrlPath;
    }
}
