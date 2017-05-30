package com.inkdrop.presentation.controllers.v1;

import com.inkdrop.application.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inkdrop.application.exceptions.ChathubBackendException;
import com.inkdrop.domain.models.Message;
import com.inkdrop.domain.models.Room;
import com.inkdrop.domain.models.User;
import com.inkdrop.infrastructure.repositories.RoomRepository;
import com.inkdrop.infrastructure.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MessagesController extends BasicController {

  @Autowired
  RoomRepository roomRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  MessageService messageService;

  @RequestMapping(method = RequestMethod.POST, path = "/v1/rooms/{uid}/messages/new")
  public ResponseEntity<String> sendMessageToRoom(@PathVariable Integer uid,
      @RequestBody Params params,
      @RequestHeader("Auth-Token") String token) {
    try {
      User sender = findByBackendToken(token, userRepository);
      Room room = roomRepository.findByUid(uid);
      Assert.notNull(room, "Room not found");
      String message = params.getContent();

      messageService.saveAndPushToFirebase(buildMessage(message, room, sender));
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (ChathubBackendException | IllegalArgumentException e) {
      log.error(e.getLocalizedMessage());
      return createErrorResponse(e);
    }
  }

  private Message buildMessage(String content, Room room, User user)
      throws ChathubBackendException {
    Message m = new Message();

    m.setRoom(room);
    m.setSender(user);
    m.setContent(content);

    if (!isValid(m)) {
      throw new ChathubBackendException("Message is not valid");
    }
    return m;
  }
}
