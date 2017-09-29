package com.inkdrop.domain.consumers;

import com.inkdrop.domain.room.Message;
import com.inkdrop.domain.room.events.MessageSavedEvent;
import com.inkdrop.infrastructure.externalServices.firebase.PushToFirebaseCommand;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class MessageSavedListener {

  @Autowired
  PushToFirebaseCommand pushToFirebaseCommand;

  @Async
  @TransactionalEventListener
  public void messageSaved(MessageSavedEvent event) {
    Message message = event.getMessage();
    message.setCreatedAt(new Date());
    pushToFirebaseCommand.pushToFirebase(message);
    log.info("Message posted");
  }
}
