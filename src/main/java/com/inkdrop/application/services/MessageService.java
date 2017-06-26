package com.inkdrop.application.services;

import com.inkdrop.application.reactive.eventnotifier.EventNotifier;
import com.inkdrop.domain.models.Message;
import com.inkdrop.infrastructure.repositories.MessageRepository;
import com.inkdrop.infrastructure.repositories.RoomRepository;
import com.inkdrop.infrastructure.repositories.UserRepository;
import com.inkdrop.presentation.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

  @Autowired
  MessageRepository messageRepository;

  @Autowired
  RoomRepository roomRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  EventNotifier eventNotifier;

  public void saveAndPushToFirebase(MessageDTO messageDTO) {
    Message message = fromDto(messageDTO);

    messageRepository.save(message);
    eventNotifier.messageSaved(message);
  }

  private Message fromDto(MessageDTO messageDto){
    Message message = new Message();
    message.setContent(messageDto.getContent());
    message.setRoom(roomRepository.findByUid(messageDto.getRoomUid()));
    message.setSender(userRepository.findByBackendAccessToken(messageDto.getUserAccessToken()));

    return message;
  }
}
