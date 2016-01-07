package com.inkdrop.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inkdrop.domain.models.Room;
import com.inkdrop.domain.models.User;
import com.inkdrop.domain.presenters.jsonModels.RoomToJson;
import com.inkdrop.domain.repositories.MessageRepository;
import com.inkdrop.domain.repositories.RoomRepository;
import com.inkdrop.domain.repositories.UserRepository;

@Component
public class RoomService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	MessageRepository messageRepository;

	@Autowired
	RoomRepository roomRepository;

	public Room findByLogin(String login){
		return roomRepository.findByLoginIgnoreCase(login);
	}

	public void joinRoom(User user, Room room){
		if(!user.getRooms().contains(room)) {
			user.getRooms().add(room);
			userRepository.save(user);
		}
	}

	public List<RoomToJson> mapToJson(List<Room> rooms) {
		List<RoomToJson> roomsJson = new ArrayList<>();
		for (Room r : rooms) {
			RoomToJson rjs = new RoomToJson(r);
			rjs.setCount(messageRepository.countByRoom(r)); // Using SQL to count instead count on list

			roomsJson.add(rjs);
		}

		return roomsJson;
	}

	public void leave(User user, Room room) {
		user.getRooms().remove(room);
		userRepository.save(user);
	}
}
