package com.inkdrop.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public void leave(User user, Room room) {
		user.getRooms().remove(room);
		userRepository.save(user);
	}
}
