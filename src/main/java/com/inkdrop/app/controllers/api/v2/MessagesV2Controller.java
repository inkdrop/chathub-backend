package com.inkdrop.app.controllers.api.v2;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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

import reactor.core.Reactor;
import reactor.event.Event;

@RestController
@EnableAutoConfiguration
public class MessagesV2Controller extends BasicV2Controller {

	private Logger log = LogManager.getLogger(MessagesV2Controller.class);

	@Autowired
	MessageRepository messageRepository;

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private Reactor r;
	
	@RequestMapping(method = RequestMethod.POST, path="/v2/rooms/{uid}/messages/new")
	public ResponseEntity<?> sendMessageToRoom(@PathVariable Integer uid,
			@RequestBody Params params,
			@RequestHeader("Auth-Token") String token){
		try{
			User sender = userRepository.findByBackendAccessToken(token);
			Room roomDestination = roomRepository.findByUid(uid);
			String content = params.getContent();
			
			Message m = buildMessage(content, roomDestination, sender);
			m = messageRepository.save(m);
			
			r.notify("message.created", Event.wrap(m));
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch(Exception e){
			log.error(e);
			return new ResponseEntity<String>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private Message buildMessage(String content, Room room, User user) {
		Message m = new Message();

		m.setRoom(room);
		m.setSender(user);
		m.setContent(content);

		return m;
	}
}
