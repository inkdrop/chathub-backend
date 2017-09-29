package com.inkdrop.presentation.controllers.v1;

import com.google.common.collect.Lists;
import com.inkdrop.domain.room.Room;
import com.inkdrop.domain.user.User;
import com.inkdrop.infrastructure.repositories.RoomRepository;
import com.inkdrop.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/rooms")
public class RoomsController extends BasicController {

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoomRepository roomRepository;

  @GetMapping
  public ResponseEntity getRoomsFromUser(@RequestHeader("Authorization") String token) {
    User user = userRepository.findByBackendAccessToken(token);
    return createSuccessfulResponse(
        Lists.newArrayList(roomRepository.findAll(user.subscribedRoomsId())));
  }

  @GetMapping("/{uid}")
  public ResponseEntity getRoomInformation(@PathVariable Integer uid,
      @RequestHeader("Authorization") String token) {
    try {
      Room room = roomRepository.findByUid(uid);
      return ResponseEntity.ok(room);
    } catch (Exception e) {
      return createErrorResponse(e);
    }
  }

  @PostMapping("/{uid}/join")
  public ResponseEntity<String> joinRoom(@PathVariable Integer uid,
      @RequestHeader("Authorization") String token) {
    try {
      User user = findByBackendToken(token, userRepository);
      Room room = roomRepository.findByUid(uid);
      Assert.notNull(room, "Room not found");

      user.subscribeToRoom(room.getId());
      userRepository.save(user);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return createErrorResponse(e);
    }
  }

  @PostMapping("/{uid}/leave")
  public ResponseEntity<String> leaveRoom(@PathVariable Integer uid,
      @RequestHeader("Authorization") String token) {
    try {
      User user = findByBackendToken(token, userRepository);
      Room room = roomRepository.findByUid(uid);
      Assert.notNull(room, "Room not found");

      user.leaveRoom(room.getId());
      userRepository.save(user);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return createErrorResponse(e);
    }
  }
}
