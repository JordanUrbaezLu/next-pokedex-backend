package com.authapi.dto;

public class FriendActionRequest {
  private Long targetUserId;

  public Long getTargetUserId() {
    return targetUserId;
  }

  public void setTargetUserId(Long targetUserId) {
    this.targetUserId = targetUserId;
  }
}
