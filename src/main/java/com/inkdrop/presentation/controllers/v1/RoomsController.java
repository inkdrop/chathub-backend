package com.inkdrop.presentation.controllers.v1;

import com.google.common.collect.Lists;
import com.inkdrop.domain.room.Room;
import com.inkdrop.domain.room.RoomService;
import com.inkdrop.domain.user.User;
import com.inkdrop.infrastructure.repositories.RoomRepository;
import com.inkdrop.infrastructure.repositories.UserRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CollectionTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @Autowired
  RoomService roomService;

  @GetMapping
  public ResponseEntity getRoomsFromUser(@RequestHeader("Auth-Token") String token) {
    User user = userRepository.findByBackendAccessToken(token);
    List<Long> rooms = user.getSubscriptions()
        .stream().map(s -> s.getRoomId())
        .collect(Collectors.toList());
    return createSuccessfulResponse(Lists.newArrayList(roomRepository.findAll(rooms)));
  }

  @GetMapping("/{uid}")
  public ResponseEntity getRoomInformation(@PathVariable Integer uid,
      @RequestHeader("Auth-Token") String token) {
    try {
      Room room = roomRepository.findByUid(uid);
      return ResponseEntity.ok(room);
    } catch (Exception e) {
      return createErrorResponse(e);
    }
  }

  @PostMapping("/{uid}/join")
  public ResponseEntity<String> joinRoom(@PathVariable Integer uid,
      @RequestHeader("Auth-Token") String token) {
    try {
      User user = findByBackendToken(token, userRepository);
      Room room = roomRepository.findByUid(uid);

      roomService.joinRoom(user, room);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return createErrorResponse(e);
    }
  }

  @PostMapping("/{uid}/leave")
  public ResponseEntity<String> leaveRoom(@PathVariable Integer uid,
      @RequestHeader("Auth-Token") String token) {
    try {
      User user = findByBackendToken(token, userRepository);
      Room room = roomRepository.findByUid(uid);

      roomService.leave(user, room);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return createErrorResponse(e);
    }
  }

  private Set<Room> formatRooms(List<Room> rooms) {
    Set<Room> formattedRooms = new HashSet<>();
    rooms.forEach(
        room -> formattedRooms.add((Room) excludeFieldsFromObject(room, new String[]{"users"})));
    return formattedRooms;
  }
}
