package com.inkdrop.infrastructure.repositories;

import com.inkdrop.domain.models.Room;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Long> {

  // to use cache key: key = "#root.args[0]"
  @Cacheable(cacheNames = "roomByUid")
  Room findByUid(Integer uid);

  Room findByFullName(String name);
}
