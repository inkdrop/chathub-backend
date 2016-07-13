package com.inkdrop.app.controllers.api.v1;

import java.util.HashSet;
import java.util.Set;

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
import com.inkdrop.app.domain.repositories.RoomRepository;
import com.inkdrop.app.domain.repositories.UserRepository;
import com.inkdrop.app.services.RoomService;

import lombok.extern.slf4j.Slf4j;

@RestController
@EnableAutoConfiguration
@Slf4j
public class RoomsController extends BasicController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	RoomService roomService;
	
	@RequestMapping(method = RequestMethod.GET, path="/v1/rooms")
	public ResponseEntity<?> getRoomsFromUser(@RequestHeader("Auth-Token") String token){
		try{
			User user = userRepository.findByBackendAccessToken(token);
			Set<Room> rooms = formatRooms(user.getRooms());
			return new ResponseEntity<>(rooms, HttpStatus.OK);
		} catch (Exception e){
			log.error(e.getLocalizedMessage());
			return createErrorResponse(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/v1/rooms/{uid}")
	public ResponseEntity<?> getRoomInformation(@PathVariable Integer uid, @RequestHeader("Auth-Token") String token){
		try {
			Room room = roomRepository.findByUid(uid);
//			room.setJoined(room.getUsers().contains(findByBackendToken(token, userRepository)));
			return new ResponseEntity<>(room, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return createErrorResponse(e);
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
			log.error(e.getLocalizedMessage());
			return createErrorResponse(e);
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
			log.error(e.getLocalizedMessage());
			return createErrorResponse(e);
		}
	}
	
	private Set<Room> formatRooms(Set<Room> rooms) {
		Set<Room> formattedRooms = new HashSet<>();
		rooms.forEach(room -> formattedRooms.add((Room) excludeFieldsFromObject(room, new String[]{"users"})));
		return formattedRooms;
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
