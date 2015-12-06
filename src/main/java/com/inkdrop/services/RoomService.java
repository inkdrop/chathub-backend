package com.inkdrop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inkdrop.domain.models.Room;
import com.inkdrop.domain.models.User;
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
}
