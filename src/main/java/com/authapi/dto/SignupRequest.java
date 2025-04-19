package com.authapi.dto;

/**
 * SignupRequest
 *
 * <p>Data Transfer Object for user registration payload.
 *
 * <p>• Fields: – email: User’s email address. – password: Plain-text password for account creation.
 * – name: User’s display name. • Used by AuthController’s POST /api/signup endpoint to bind
 * incoming JSON.
 */
public class SignupRequest {
  private String email;
  private String password;
  private String name;

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
