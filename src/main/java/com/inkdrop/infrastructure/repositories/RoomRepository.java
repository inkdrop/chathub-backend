package com.inkdrop.infrastructure.repositories;

import com.inkdrop.domain.room.Room;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Long> {

  @EntityGraph("with-messages")
  Room findByUid(Integer uid);

  Room findByFullName(String name);
}
