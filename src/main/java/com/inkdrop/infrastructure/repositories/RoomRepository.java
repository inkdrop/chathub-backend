package com.inkdrop.infrastructure.repositories;

import com.inkdrop.domain.room.Room;
import com.inkdrop.domain.user.User;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Long> {

  // to use cache key: key = "#root.args[0]"
  @EntityGraph("with-messages")
  Room findByUid(Integer uid);

  Room findByFullName(String name);

  List<Room> findByUsers(List<User> user);
}
