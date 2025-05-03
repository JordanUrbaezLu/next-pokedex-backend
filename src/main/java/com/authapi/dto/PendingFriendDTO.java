package com.authapi.dto;

import java.time.Instant;

public record PendingFriendDTO(Long id, String email, String name, Instant requestedAt) {}
