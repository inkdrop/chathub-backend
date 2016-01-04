package com.inkdrop.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inkdrop.domain.models.Room;
import com.inkdrop.domain.models.User;
import com.inkdrop.domain.presenters.jsonModels.RoomToJson;
import com.inkdrop.domain.repositories.UserRepository;

@Component
public class RoomService {

	@Autowired
	UserRepository userRepository;

	public void joinRoom(User user, Room room){
		if(!user.getRooms().contains(room)) {
			user.getRooms().add(room);
			userRepository.save(user);
		}
	}

	public List<RoomToJson> mapToJson(List<Room> rooms) {
		List<RoomToJson> roomsJson = new ArrayList<>();
		for (Room r : rooms)
			roomsJson.add(new RoomToJson(r));

		return roomsJson;
	}
}
