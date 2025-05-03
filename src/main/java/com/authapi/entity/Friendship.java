package com.authapi.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(
    name = "friendships",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"requester_id", "recipient_id"})})
public class Friendship {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  private User requester;

  @ManyToOne(optional = false)
  private User recipient;

  private boolean accepted = false;

  private Instant createdAt;

  // === Getters and Setters ===

  public Long getId() {
    return id;
  }

  public User getRequester() {
    return requester;
  }

  public void setRequester(User requester) {
    this.requester = requester;
  }

  public User getRecipient() {
    return recipient;
  }

  public void setRecipient(User recipient) {
    this.recipient = recipient;
  }

  public boolean isAccepted() {
    return accepted;
  }

  public void setAccepted(boolean accepted) {
    this.accepted = accepted;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }
}
