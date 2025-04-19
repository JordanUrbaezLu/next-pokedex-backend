package com.authapi.controller;

import com.authapi.entity.User;
import com.authapi.repository.UserRepository;
import com.authapi.security.JwtUtil;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AccountController
 *
 * <p>Exposes a single GET endpoint under `/api/account` to return the authenticated user's basic
 * profile (email and name).
 *
 * <p>• Expects an HTTP “Authorization: Bearer <token>” header. • Validates and parses the JWT via
 * JwtUtil to extract the user’s email. • Loads the User from the database using UserRepository. •
 * Returns HTTP 200 with { email, name } on success. • Returns 401 if the token is
 * missing/invalid/expired, or 404 if the user isn’t found.
 */
@RestController
@RequestMapping("/api")
public class AccountController {

  @Autowired private JwtUtil jwtUtil;

  @Autowired private UserRepository userRepository;

  @GetMapping("/account")
  public ResponseEntity<?> getAccount(@RequestHeader("Authorization") String authHeader) {
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return ResponseEntity.status(401).body("Missing or invalid Authorization header");
    }

    String token = authHeader.substring(7); // Remove "Bearer "
    String email;

    try {
      email = jwtUtil.extractEmail(token);
    } catch (Exception e) {
      return ResponseEntity.status(401).body("Invalid or expired token");
    }

    Optional<User> userOpt = userRepository.findByEmail(email);

    if (userOpt.isEmpty()) {
      return ResponseEntity.status(404).body("User not found");
    }

    User user = userOpt.get();
    return ResponseEntity.ok(
        Map.of(
            "email", user.getEmail(),
            "name", user.getName()));
  }
}
