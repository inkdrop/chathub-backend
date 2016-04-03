package com.inkdrop.app.controllers.api.v1;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inkdrop.app.domain.formatter.FormatterFactory;
import com.inkdrop.app.domain.formatter.jsonModels.MessagesPageJson;
import com.inkdrop.app.domain.formatter.jsonModels.RoomJson;
import com.inkdrop.app.domain.models.Message;
import com.inkdrop.app.domain.models.Room;
import com.inkdrop.app.domain.models.User;
import com.inkdrop.app.domain.repositories.MessageRepository;
import com.inkdrop.app.domain.repositories.RoomRepository;
import com.inkdrop.app.domain.repositories.UserRepository;
import com.inkdrop.app.services.RoomService;

@RestController
@EnableAutoConfiguration
public class RoomsController {

	private Logger log = LogManager.getLogger(RoomsController.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	MessageRepository messageRepository;

	@Autowired
	RoomService roomService;

	@RequestMapping(method = RequestMethod.GET, path="/v1/rooms/{name}")
	public ResponseEntity<?> getRoomInformation(@PathVariable String name, @RequestHeader("Auth-Token") String token){
		try {
			Room room = roomRepository.findByLoginIgnoreCase(name);
			User user = userRepository.findByBackendAccessToken(token);
			room.setJoined(user.getRooms().contains(room)); // TODO fix

			String json = FormatterFactory.getFormatter(Room.class).toJson(room);

			return new ResponseEntity<String>(json, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path="/v1/rooms")
	public ResponseEntity<?> getRoomsFromUser(@RequestHeader("Auth-Token") String token){
		try{
			User user = userRepository.findByBackendAccessToken(token);
			List<RoomJson> rooms = mapToJson(user.getRooms());

			return new ResponseEntity<List<RoomJson>>(rooms, HttpStatus.OK);
		} catch (Exception e){
			return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.POST, path="/v1/rooms/{name}/join")
	public ResponseEntity<?> joinRoom(@PathVariable String name, @RequestHeader("Auth-Token") String token){
		try{
			User user = userRepository.findByBackendAccessToken(token);
			Room room = roomRepository.findByLoginIgnoreCase(name);

			roomService.joinRoom(user, room);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>("Error: "+e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.POST, path="/v1/rooms/{name}/leave")
	public ResponseEntity<?> leaveRoom(@PathVariable String name, @RequestHeader("Auth-Token") String token){
		try{
			User user = userRepository.findByBackendAccessToken(token);
			Room room = roomRepository.findByLoginIgnoreCase(name);

			roomService.leave(user, room);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>("Error: "+e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path="/v1/rooms/{name}/messages", produces="application/json; charset=UTF-8")
	public ResponseEntity<?> findLast10Messages(@PathVariable String name, @PathParam("page") Integer page){
		try{
			Room room = roomRepository.findByLoginIgnoreCase(name);
			page = page == null ? 0 : page;
			Page<Message> pageResult = messageRepository.findByRoom(room, getPageable(page));
			MessagesPageJson result = formatResult(pageResult);

			return new ResponseEntity<MessagesPageJson>(result, HttpStatus.OK);
		} catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity<String>("Error: "+e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	private MessagesPageJson formatResult(Page<Message> pageResult) {
		return new MessagesPageJson(pageResult);
	}

	private Pageable getPageable(Integer page) {
		return new PageRequest(page, 50, Sort.Direction.DESC, "createdAt");
	}

	private List<RoomJson> mapToJson(List<Room> rooms) {
		List<RoomJson> roomsJson = new ArrayList<>();
		for (Room r : rooms) {
			RoomJson json = new RoomJson(r);
			json.setCount(messageRepository.countByRoom(r)); // Using SQL to count instead count on list
			roomsJson.add(json);
		}

		return roomsJson;
	}
}
