package com.inkdrop.app.controllers.api.v1;

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

import com.inkdrop.app.domain.models.Room;
import com.inkdrop.app.domain.models.User;
import com.inkdrop.app.domain.repositories.MessageRepository;
import com.inkdrop.app.domain.repositories.RoomRepository;
import com.inkdrop.app.domain.repositories.UserRepository;
import com.inkdrop.app.services.RoomService;

@RestController
@EnableAutoConfiguration
public class RoomsController extends BasicController {

	private Logger logger = LogManager.getLogger(RoomsController.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	MessageRepository messageRepository;

	@Autowired
	RoomService roomService;
	
	@RequestMapping(method = RequestMethod.GET, path="/v1/rooms/{uid}")
	public ResponseEntity<Object> getRoomInformation(@PathVariable Integer uid, @RequestHeader("Auth-Token") String token){
		try {
			Room room = roomRepository.findByUid(uid);
			room.setJoined(room.getUsers().contains(findByBackendToken(token, userRepository)));
			return new ResponseEntity<>(room, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e);
			return new ResponseEntity<>(exception(e), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path="/v1/rooms")
	public ResponseEntity<Object> getRoomsFromUser(@RequestHeader("Auth-Token") String token){
		try{
			logger.info("Loading rooms from user");
			User user = userRepository.findByBackendAccessToken(token);
			logger.info("User loaded");
			return createSuccessfulResponse(jsonWithExclusions(user.getRooms(), "users", "organization"));
		} catch (Exception e){
			logger.error(e);
			return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.POST, path="/v1/rooms/{uid}/join")
	public ResponseEntity<String> joinRoom(@PathVariable Integer uid, @RequestHeader("Auth-Token") String token){
		try{
			User user = findByBackendToken(token, userRepository);
			Room room = roomRepository.findByUid(uid);

			roomService.joinRoom(user, room);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch(Exception e) {
			logger.error(e);
			return new ResponseEntity<>(exception(e), HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@RequestMapping(method = RequestMethod.POST, path="/v1/rooms/{uid}/leave")
	public ResponseEntity<String> leaveRoom(@PathVariable Integer uid, @RequestHeader("Auth-Token") String token){
		try{
			User user = findByBackendToken(token, userRepository);
			Room room = roomRepository.findByUid(uid);

			roomService.leave(user, room);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch(Exception e) {
			logger.error(e);
			return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	//	@RequestMapping(method = RequestMethod.GET, path="/v1/rooms/{uid}/messages", produces="application/json; charset=UTF-8")
//	public ResponseEntity<?> findLast10Messages(@PathVariable Integer uid, @PathParam("page") Integer page){
//		try{
//			Room room = roomRepository.findByUid(uid);
//			page = page == null ? 0 : page;
//			Page<Message> pageResult = messageRepository.findByRoom(room, getPageable(page));
//			MessagesPageJson result = formatResult(pageResult);
//
//			return new ResponseEntity<MessagesPageJson>(result, HttpStatus.OK);
//		} catch(Exception e){
//			e.printStackTrace();
//			return new ResponseEntity<String>("Error: "+e.getMessage(), HttpStatus.BAD_REQUEST);
//		}
//	}
//
//	private MessagesPageJson formatResult(Page<Message> pageResult) {
//		return new MessagesPageJson(pageResult);
//	}
//
//	private Pageable getPageable(Integer page) {
//		return new PageRequest(page, 50, Sort.Direction.DESC, "createdAt");
//	}
}
