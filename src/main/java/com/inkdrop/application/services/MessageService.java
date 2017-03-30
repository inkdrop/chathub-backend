package com.inkdrop.application.services;

import com.inkdrop.application.reactive.eventnotifier.EventNotifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inkdrop.domain.models.Message;
import com.inkdrop.infrastructure.repositories.MessageRepository;

@Service
public class MessageService {

  @Autowired
  MessageRepository messageRepository;
  
  @Autowired
  EventNotifier eventNotifier;
  
  public void saveAndPushToFirebase(Message message) {
    messageRepository.save(message);
    eventNotifier.messageSaved(message);
  }
}
