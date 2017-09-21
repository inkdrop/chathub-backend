package com.inkdrop.domain.message.events;

import com.inkdrop.domain.message.Message;
import lombok.Data;

@Data
public class MessageSavedEvent {
  public Message message;
}
