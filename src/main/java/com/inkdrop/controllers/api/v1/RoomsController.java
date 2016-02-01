package com.inkdrop.controllers.api.v1;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inkdrop.domain.models.Message;
import com.inkdrop.domain.models.Room;
import com.inkdrop.domain.models.User;
import com.inkdrop.domain.presenters.RoomPresenter;
import com.inkdrop.domain.presenters.jsonModels.MessageToJson;
import com.inkdrop.domain.presenters.jsonModels.RoomToJson;
import com.inkdrop.exceptions.ChathubBackendException;
import com.inkdrop.helpers.InstantHelper;
import com.inkdrop.services.GitHubService;
import com.inkdrop.services.MessageService;
import com.inkdrop.services.RoomService;
import com.inkdrop.services.UserService;

@RestController
@EnableAutoConfiguration
public class RoomsController {

	private Logger log = LogManager.getLogger(RoomsController.class);

	@Autowired
	GitHubService gitHubService;

	@Autowired
	UserService userService;

	@Autowired
	RoomService roomService;

	@Autowired
	MessageService messageService;

	@RequestMapping(method = RequestMethod.GET, path="/v1/rooms/{name}")
	public ResponseEntity<?> getRoomInformation(@PathVariable String name, @RequestHeader("Auth-Token") String token){
		try {
			Room room = roomService.findByLogin(name);
			User user = userService.findByBackendToken(token);

			if(room == null || InstantHelper.biggerThanSixHours(room.getUpdatedAt()))
				room = gitHubService.createOrUpdateRoom(name, user.getAccessToken());

			String roomJson = new RoomPresenter(room).toJson();
			joinRoom(room.getLogin(), token);

			return new ResponseEntity<String>(roomJson, HttpStatus.OK);
		} catch (ChathubBackendException e) {
			log.error(e);
			return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path="/v1/rooms")
	public ResponseEntity<?> getRoomsFromUser(@RequestHeader("Auth-Token") String token){
		try{
			User user = userService.findByBackendToken(token);
			List<RoomToJson> rooms = roomService.mapToJson(user.getRooms());
			return new ResponseEntity<List<RoomToJson>>(rooms, HttpStatus.OK);
		} catch (Exception e){
			return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.POST, path="/v1/rooms/{name}/join")
	public ResponseEntity<?> joinRoom(@PathVariable String name, @RequestHeader("Auth-Token") String token){
		try{
			User user = userService.findByBackendToken(token);
			Room room = roomService.findByLogin(name);

			roomService.joinRoom(user, room);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>("Error: "+e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.POST, path="/v1/rooms/{name}/leave")
	public ResponseEntity<?> leaveRoom(@PathVariable String name, @RequestHeader("Auth-Token") String token){
		try{
			User user = userService.findByBackendToken(token);
			Room room = roomService.findByLogin(name);

			roomService.leave(user, room);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>("Error: "+e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path="/v1/rooms/{name}/messages")
	public ResponseEntity<?> findLast10Messages(@PathVariable String name){
		try{
			Room room = roomService.findByLogin(name);

			List<Message> messages = messageService.findLast10(room);
			List<MessageToJson> response = messageService.formatMessages(messages);

			return new ResponseEntity<List<MessageToJson>>(response, HttpStatus.OK);
		} catch(Exception e){
			return new ResponseEntity<String>("Error: "+e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
