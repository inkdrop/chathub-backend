package com.inkdrop.domain.room.events;

import com.inkdrop.domain.room.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomCreatedEvent {
  Room room;
}
