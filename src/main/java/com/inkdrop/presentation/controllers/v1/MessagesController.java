package com.inkdrop.presentation.controllers.v1;

import com.inkdrop.domain.message.MessageService;
import com.inkdrop.presentation.dto.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  MessageService messageService;

  @RequestMapping(method = RequestMethod.POST, path = "/v1/rooms/{uid}/messages/new")
  public ResponseEntity<String> sendMessageToRoom(
      @PathVariable Integer uid,
      @RequestBody Params params,
      @RequestHeader("Auth-Token") String token) {
    try {
      messageService.saveAndPushToFirebase(new MessageDTO(token, uid, params.getContent()));
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (IllegalArgumentException e) {
      log.error(e.getLocalizedMessage());
      return createErrorResponse(e);
    }
  }
}
