package com.inkdrop.controllers.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inkdrop.domain.models.Room;
import com.inkdrop.domain.models.User;
import com.inkdrop.domain.repositories.RoomRepostitory;
import com.inkdrop.domain.repositories.UserRepository;
import com.inkdrop.services.GitHubService;

@RestController
@EnableAutoConfiguration
public class RoomsController {

	@Autowired
	RoomRepostitory roomRepostitory;

	@Autowired
	GitHubService gitHubService;

	@Autowired
	UserRepository userRepostitory;

	@RequestMapping(method = RequestMethod.GET, path="/v1/room/{name}")
	public ResponseEntity<?> getRoomInformation(@PathVariable String name, @RequestHeader("Auth-Token") String token){
		try {
			Room room = roomRepostitory.findByLoginIgnoreCase(name);
			if(room == null)
				room = gitHubService.createRoom(name, getUserByBackendToken(token).getAccessToken());
			return new ResponseEntity<Room>(room, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.POST, path="/v1/room/{name}/join")
	public ResponseEntity<?> joinRoom(@PathVariable String name, @RequestHeader("Auth-Token") String token){
		try{
			User user = getUserByBackendToken(token);
			Room room = roomRepostitory.findByLoginIgnoreCase(name);
			if(!user.getRooms().contains(room)) {
				user.getRooms().add(room);
				userRepostitory.save(user);
				return new ResponseEntity<String>("Joined", HttpStatus.OK);
			} else
				return new ResponseEntity<String>("Already added", HttpStatus.NOT_MODIFIED);
		}catch(Exception e){
			return new ResponseEntity<String>("Error: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private User getUserByBackendToken(String token) {
		return userRepostitory.findByBackendAccessToken(token);
	}
}
