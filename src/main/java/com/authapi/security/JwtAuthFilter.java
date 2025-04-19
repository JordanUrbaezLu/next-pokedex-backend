package com.authapi.security;

import com.authapi.entity.User;
import com.authapi.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * JwtAuthFilter
 *
 * <p>A servlet filter that enforces JWT-based authentication on incoming requests, except for the
 * public Pokémon endpoints.
 *
 * <p>• Skips filtering for any path under "/api/pokemon/". • Extracts and validates the Bearer
 * token from the "Authorization" header. • On successful validation, loads the corresponding User
 * from the database and populates Spring Security’s context with an authenticated principal. • On
 * token error or absence, responds with HTTP 401 Unauthorized.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  @Autowired private JwtUtil jwtUtil;

  @Autowired private UserRepository userRepository;

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    // Skip JWT auth for everything under /api/pokemon/
    String path = request.getServletPath();
    return path.startsWith("/api/pokemon/");
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String token = authHeader.substring(7);

      try {
        String email = jwtUtil.extractEmail(token);
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
          User user = userOpt.get();
          UserDetails userDetails =
              org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                  .password(user.getPassword())
                  .authorities("USER")
                  .build();

          UsernamePasswordAuthenticationToken auth =
              new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());

          SecurityContextHolder.getContext().setAuthentication(auth);
        }
      } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Invalid or expired token");
        return;
      }
    }

    filterChain.doFilter(request, response);
  }
}
