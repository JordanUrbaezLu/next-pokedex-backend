package com.authapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

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

  private Instant createdAt;
  private Instant updatedAt;

  @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private Set<Friendship> sentFriendRequests = new HashSet<>();

  @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private Set<Friendship> receivedFriendRequests = new HashSet<>();

  @PrePersist
  protected void onCreate() {
    createdAt = updatedAt = Instant.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = Instant.now();
  }

  // Getters and setters

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

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public Set<Friendship> getSentFriendRequests() {
    return sentFriendRequests;
  }

  public void setSentFriendRequests(Set<Friendship> sentFriendRequests) {
    this.sentFriendRequests = sentFriendRequests;
  }

  public Set<Friendship> getReceivedFriendRequests() {
    return receivedFriendRequests;
  }

  public void setReceivedFriendRequests(Set<Friendship> receivedFriendRequests) {
    this.receivedFriendRequests = receivedFriendRequests;
  }
}
