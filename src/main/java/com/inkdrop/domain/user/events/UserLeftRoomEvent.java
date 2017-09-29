package com.inkdrop.domain.user.events;

import com.inkdrop.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLeftRoomEvent {
  private User user;
  private Long roomId;
}
