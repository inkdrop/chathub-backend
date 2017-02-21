package com.inkdrop.app.controllers.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inkdrop.app.domain.models.Message;
import com.inkdrop.app.domain.models.Room;
import com.inkdrop.app.domain.models.User;
import com.inkdrop.app.domain.repositories.MessageRepository;
import com.inkdrop.app.domain.repositories.RoomRepository;
import com.inkdrop.app.domain.repositories.UserRepository;
import com.inkdrop.app.eventnotifier.EventNotifier;
import com.inkdrop.app.exceptions.ChathubBackendException;

import lombok.extern.slf4j.Slf4j;

@RestController
@EnableAutoConfiguration
@Slf4j
public class MessagesController extends BasicController {

	@Autowired MessageRepository messageRepository;

	@Autowired RoomRepository roomRepository;

	@Autowired UserRepository userRepository;
	
	@Autowired EventNotifier eventNotifier;
	
	@RequestMapping(method = RequestMethod.POST, path="/v1/rooms/{uid}/messages/new")
	public ResponseEntity<String> sendMessageToRoom(@PathVariable Integer uid,
			@RequestBody Params params,
			@RequestHeader("Auth-Token") String token){
		try{
			User sender = userRepository.findByBackendAccessToken(token);
			Room room = roomRepository.findByUid(uid);
			String message = params.getContent();
			
			Message m = messageRepository.save(buildMessage(message, room, sender));

			eventNotifier.saveMessage(buildMessage(message, room, sender));

//			eventNotifier.messageSaved(m);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch(ChathubBackendException e){
			log.error(e.getLocalizedMessage());
			return createErrorResponse(e);
		}
	}
	
	private Message buildMessage(String content, Room room, User user) throws ChathubBackendException {
		Message m = new Message();

		m.setRoom(room);
		m.setSender(user);
		m.setContent(content);

		if(!isValid(m))
			throw new ChathubBackendException("Message is not valid");
		return m;
	}
}
