package com.inkdrop.domain;

public enum EventType {
  LOGIN("login"),
  JOIN_ROOM("join_room"),
  LEAVE_ROOM("leave_room"),
  MESSAGE_SENT("message_sent"),
  ENTER_ROOM("enter_room"),
  NEW_USER("new_user");

  private String key;

  EventType(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
