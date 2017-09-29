package com.inkdrop.domain.user.events;

import com.inkdrop.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreatedEvent {

  private User user;
}
