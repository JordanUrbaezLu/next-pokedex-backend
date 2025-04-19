package com.authapi.entity;

import jakarta.persistence.*;

/**
 * User
 *
 * <p>JPA entity representing a user account in the application.
 *
 * <p>• Mapped to the “users” table in the database. • Fields: – id (Long): Auto‑generated primary
 * key. – email (String): User’s unique email address. – name (String): Display name for the user. –
 * password (String): Hashed password for authentication.
 *
 * <p>Used by: • AuthController for signup/login flows. • AccountController to look up profile data.
 */
@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;
  private String name;
  private String password;

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
