package com.inkdrop.domain.room.factory;

import com.inkdrop.application.helpers.TokenGeneratorHelper;
import com.inkdrop.domain.room.Message;
import com.inkdrop.domain.user.User;
import java.util.UUID;

public class MessageFactory {

  public static Message createMessage(String content, User sender) {
    Message message = new Message();
    message.setContent(content);
    message.setSender(sender);
    message.setUid(TokenGeneratorHelper.newToken(35));

    return message;
  }
}
