package com.authapi.dto;

import java.time.Instant;

public record FriendDTO(Long id, String email, String name, Instant acceptedAt) {}
