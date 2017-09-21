package com.inkdrop.domain.room;

import com.inkdrop.domain.user.User;
import com.inkdrop.infrastructure.repositories.RoomRepository;
import com.inkdrop.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomService {

  @Autowired
  RoomRepository roomRepository;

  @Autowired
  UserRepository userRepository;

  @Transactional
  public void joinRoom(User user, Room room) {
    user = userRepository.findOne(user.getId());
    room = roomRepository.findOne(room.getId());
    if (user.getRooms().contains(room)) {
      return;
    } else {
      user.getRooms().add(room);
    }

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
