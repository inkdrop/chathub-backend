package com.inkdrop.domain.message;

import com.inkdrop.domain.message.Message;
import com.inkdrop.infrastructure.repositories.MessageRepository;
import com.inkdrop.infrastructure.repositories.RoomRepository;
import com.inkdrop.infrastructure.repositories.UserRepository;
import com.inkdrop.presentation.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
  private ApplicationEventPublisher publisher;

  public void saveAndPushToFirebase(MessageDTO messageDTO) {
    Message message = fromDto(messageDTO);

    messageRepository.save(message);
    publisher.publishEvent(message);
  }

  private Message fromDto(MessageDTO messageDto) {
    Message message = new Message();
    message.setContent(messageDto.getContent());
    message.setRoom(roomRepository.findByUid(messageDto.getRoomUid()));
    message.setSender(userRepository.findByBackendAccessToken(messageDto.getUserAccessToken()));

    return message;
  }
}
