package com.inkdrop.presentation.controllers.v1;

import com.inkdrop.domain.room.Room;
import com.inkdrop.domain.room.factory.MessageFactory;
import com.inkdrop.domain.user.User;
import com.inkdrop.infrastructure.repositories.RoomRepository;
import com.inkdrop.infrastructure.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
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

@RestController
@Slf4j
public class MessagesController extends BasicController {

  @Autowired
  RoomRepository roomRepository;

  @Autowired
  UserRepository userRepository;

  @RequestMapping(method = RequestMethod.POST, path = "/v1/rooms/{uid}/messages/new")
  public ResponseEntity<String> sendMessage(
      @PathVariable Integer uid,
      @RequestBody Params params,
      @RequestHeader("Auth-Token") String token) {
    try {
      Room room = roomRepository.findByUid(uid);
      Assert.notNull(room, "Room not found");
      User sender = userRepository.findByBackendAccessToken(token);
      room.sendMessage(MessageFactory.createMessage(params.getContent(), sender));
      roomRepository.save(room);

      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (IllegalArgumentException e) {
      log.error(e.getLocalizedMessage());
      return createErrorResponse(e, HttpStatus.NOT_FOUND);
    }
  }
}
