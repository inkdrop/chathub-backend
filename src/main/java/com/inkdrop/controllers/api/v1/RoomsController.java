package com.inkdrop.controllers.api.v1;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.inkdrop.domain.repositories.MessageRepository;
import com.inkdrop.domain.repositories.RoomRepository;
import com.inkdrop.domain.repositories.UserRepository;
import com.inkdrop.services.GitHubService;
import com.inkdrop.services.RoomService;

@RestController
@EnableAutoConfiguration
@CrossOrigin(origins={"http://127.0.0.1:3000"})
public class RoomsController {

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	GitHubService gitHubService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoomService roomService;

	@Autowired
	MessageRepository messageRepository;

	@RequestMapping(method = RequestMethod.GET, path="/v1/room/{name}")
	public ResponseEntity<?> getRoomInformation(@PathVariable String name, @RequestHeader("Auth-Token") String token){
		try {
			Room room = getRoomByLogin(name);
			if(room == null)
				room = gitHubService.createRoom(name, getUserByBackendToken(token).getAccessToken());
			String roomJson = new RoomPresenter(room).toJson();

			return new ResponseEntity<String>(roomJson, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.POST, path="/v1/room/{name}/join")
	public ResponseEntity<?> joinRoom(@PathVariable String name, @RequestHeader("Auth-Token") String token){
		try{
			User user = getUserByBackendToken(token);
			Room room = getRoomByLogin(name);

			roomService.joinRoom(user, room);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>("Error: "+e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path="/v1/room/{name}/messages")
	public ResponseEntity<?> findLast10Messages(@PathVariable String name){
		try{
			Room room = getRoomByLogin(name);
			List<Message> messages = messageRepository.findLast10ByRoom(room);
			List<MessageToJson> response = formatMessages(messages);

			return new ResponseEntity<List<MessageToJson>>(response, HttpStatus.OK);
		} catch(Exception e){
			return new ResponseEntity<String>("Error: "+e.getMessage(), HttpStatus.BAD_REQUEST);

		}
	}

	private List<MessageToJson> formatMessages(List<Message> messages) {
		List<MessageToJson> m = new ArrayList<>();
		for (Message message : messages)
			m.add(new MessageToJson(message));

		return m;
	}

	private Room getRoomByLogin(String name) {
		return roomRepository.findByLoginIgnoreCase(name);
	}

	private User getUserByBackendToken(String token) {
		return userRepository.findByBackendAccessToken(token);
	}
}
