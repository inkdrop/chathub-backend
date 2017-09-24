package com.inkdrop.domain.message.events;

import com.inkdrop.domain.room.Message;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageSavedEvent implements Serializable{
  private Message message;
}
