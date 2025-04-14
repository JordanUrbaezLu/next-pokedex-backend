package com.authapi.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ConsumerIdFilter extends OncePerRequestFilter {

    private static final String HEADER_NAME = "NEXT_POKEDEX_CONSUMER_ID";
    private static final String VALID_CONSUMER_ID = "npci_d8f71b9d4ab643f8";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String consumerId = request.getHeader(HEADER_NAME);

        if (consumerId == null || consumerId.isBlank()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing header: " + HEADER_NAME);
            return;
        }

        if (!VALID_CONSUMER_ID.equals(consumerId)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid " + HEADER_NAME);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
