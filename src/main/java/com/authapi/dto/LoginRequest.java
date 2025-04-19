package com.authapi.dto;

/**
 * LoginRequest
 *
 * <p>Data Transfer Object for user login credentials.
 *
 * <p>• Fields: – email: User’s email address. – password: Plain-text password. • Used by
 * AuthController’s POST /api/login endpoint to bind incoming JSON.
 */
public class LoginRequest {
  private String email;
  private String password;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
