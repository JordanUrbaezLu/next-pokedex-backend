package com.authapi.controller;

import com.authapi.dto.LoginRequest;
import com.authapi.dto.SignupRequest;
import com.authapi.entity.User;
import com.authapi.repository.UserRepository;
import com.authapi.security.JwtUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController
 *
 * <p>Handles user registration and authentication under `/api`:
 *
 * <p>• POST /api/signup – Accepts SignupRequest (email, name, password). – Validates uniqueness of
 * email. – Hashes password, saves a new User, and returns a JWT + user info. – Returns 400 if email
 * already exists.
 *
 * <p>• POST /api/login – Accepts LoginRequest (email, password). – Verifies credentials against
 * stored hash. – Returns a JWT along with email and name on success. – Returns 401 for invalid
 * credentials or missing user.
 *
 * <p>Delegates password hashing to PasswordEncoder/BCrypt, and JWT creation/validation to JwtUtil.
 */
@RestController
@RequestMapping("/api")
public class AuthController {

  @Autowired private UserRepository userRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private JwtUtil jwtUtil;

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
      return ResponseEntity.badRequest().body("Email already exists");
    }

    User user = new User();
    user.setEmail(request.getEmail());
    user.setName(request.getName());
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    userRepository.save(user);

    String token = jwtUtil.generateToken(user);
    Map<String, Object> response = new HashMap<>();
    response.put("token", token);
    response.put("user", user);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

    if (optionalUser.isEmpty()) {
      return ResponseEntity.status(401).body("Invalid credentials");
    }

    User user = optionalUser.get();

    boolean passwordMatches = BCrypt.checkpw(request.getPassword(), user.getPassword());

    if (!passwordMatches) {
      return ResponseEntity.status(401).body("Invalid credentials");
    }

    String token = jwtUtil.generateToken(user);

    return ResponseEntity.ok()
        .body(
            Map.of(
                "token", token,
                "email", user.getEmail(),
                "name", user.getName()));
  }
}
