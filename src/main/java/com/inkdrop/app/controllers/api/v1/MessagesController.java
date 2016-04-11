package com.inkdrop.app.controllers.api.v1;

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

import com.inkdrop.app.domain.models.Room;
import com.inkdrop.app.domain.models.User;
import com.inkdrop.app.domain.repositories.RoomRepository;
import com.inkdrop.app.domain.repositories.UserRepository;
import com.inkdrop.app.exceptions.ChathubBackendException;
import com.inkdrop.app.services.MessageService;

@RestController
@EnableAutoConfiguration
public class MessagesController extends BasicController{

	private Logger log = LogManager.getLogger(MessagesController.class);

	@Autowired
	MessageService messageService;

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(method = RequestMethod.POST, path="/v1/rooms/{uid}/messages/new")
	public ResponseEntity<?> sendMessageToRoom(@PathVariable Integer uid,
			@RequestBody Params params,
			@RequestHeader("Auth-Token") String token){
		try{
			User sender = userRepository.findByBackendAccessToken(token);
			Room roomDestination = roomRepository.findByUid(uid);
			String message = params.getContent();

			messageService.send(sender, roomDestination, message);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}catch(ChathubBackendException e){
			log.error(e);
			return new ResponseEntity<String>("Error: "+e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
