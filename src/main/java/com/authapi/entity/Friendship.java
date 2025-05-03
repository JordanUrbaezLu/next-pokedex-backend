package com.authapi.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "friendships_v2")
public class Friendship {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User requester;

  @ManyToOne
  @JoinColumn(name = "friend_id", nullable = false)
  private User receiver;

  private boolean accepted = false;

  private Instant requestedAt = Instant.now();
  private Instant acceptedAt;

  // Getters and setters

  public Long getId() {
    return id;
  }

  public User getRequester() {
    return requester;
  }

  public void setRequester(User requester) {
    this.requester = requester;
  }

  public User getReceiver() {
    return receiver;
  }

  public void setReceiver(User receiver) {
    this.receiver = receiver;
  }

  public boolean isAccepted() {
    return accepted;
  }

  public void setAccepted(boolean accepted) {
    this.accepted = accepted;
  }

  public Instant getRequestedAt() {
    return requestedAt;
  }

  public void setRequestedAt(Instant requestedAt) {
    this.requestedAt = requestedAt;
  }

  public Instant getAcceptedAt() {
    return acceptedAt;
  }

  public void setAcceptedAt(Instant acceptedAt) {
    this.acceptedAt = acceptedAt;
  }
}
