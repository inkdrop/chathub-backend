package com.inkdrop.infrastructure.repositories;

import com.inkdrop.domain.models.Room;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Long> {

  @Cacheable(cacheNames = "roomByUid", key = "#root.args[0]")
  Room findByUid(Integer uid);

  Room findByFullName(String name);
}
