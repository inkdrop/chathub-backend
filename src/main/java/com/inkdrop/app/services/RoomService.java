package com.inkdrop.app.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inkdrop.app.domain.formatter.jsonModels.RoomJson;
import com.inkdrop.app.domain.models.Room;
import com.inkdrop.app.domain.models.User;
import com.inkdrop.app.domain.repositories.MessageRepository;
import com.inkdrop.app.domain.repositories.RoomRepository;
import com.inkdrop.app.domain.repositories.UserRepository;

@Service
public class RoomService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	MessageRepository messageRepository;

	@Autowired
	RoomRepository roomRepository;

	public void joinRoom(User user, Room room){
		if(!user.getRooms().contains(room)) {
			user.getRooms().add(room);
			userRepository.save(user);
		}
	}

	private List<RoomJson> mapToJson(List<Room> rooms) {
		List<RoomJson> roomsJson = new ArrayList<>();
		for (Room r : rooms) {
			RoomJson roomJson = new RoomJson(r);
			roomJson.setCount(messageRepository.countByRoom(r)); // Using SQL to count instead count on list
			roomsJson.add(roomJson);
		}

		return roomsJson;
	}

	public void leave(User user, Room room) {
		user.getRooms().remove(room);
		userRepository.save(user);
	}
}
