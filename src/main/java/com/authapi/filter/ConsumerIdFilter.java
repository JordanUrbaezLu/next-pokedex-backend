package com.authapi.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * ConsumerIdFilter
 *
 * <p>A servlet filter that protects all incoming requests by verifying a custom consumer ID header.
 *
 * <p>• Header: NEXT_POKEDEX_CONSUMER_ID • Validates that the header is present and matches a
 * configured value. • Returns HTTP 401 Unauthorized with an error message if the header is missing
 * or invalid. • If valid, passes the request down the filter chain.
 *
 * <p>Use this to ensure only authorized callers (e.g., your frontend) can access the Pokémon
 * endpoints.
 */
@Component
public class ConsumerIdFilter extends OncePerRequestFilter {

  private static final String HEADER_NAME = "NEXT_POKEDEX_CONSUMER_ID";
  private static final String VALID_CONSUMER_ID = "npci_d8f71b9d4ab643f8";

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

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
