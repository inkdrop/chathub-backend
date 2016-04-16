package com.inkdrop.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inkdrop.app.domain.models.Room;
import com.inkdrop.app.domain.models.User;
import com.inkdrop.app.domain.repositories.RoomRepository;
import com.inkdrop.app.domain.repositories.UserRepository;

@Service
public class RoomService {

	@Autowired
	RoomRepository roomRepository;
	
	@Autowired UserRepository userRepository;

	@Transactional
	public void joinRoom(User user, Room room){
		user = userRepository.findOne(user.getId());
		room = roomRepository.findOne(room.getId());
		if(user.getRooms().contains(room)) 
			return;
		else
			user.getRooms().add(room);
		
		userRepository.save(user);
	}

	@Transactional
	public void leave(User user, Room room) {
		user = userRepository.findOne(user.getId());
		room = roomRepository.findOne(room.getId());
		user.getRooms().remove(room);
		
		userRepository.save(user);
	}
}
