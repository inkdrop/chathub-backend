package com.inkdrop.app.domain.repositories;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import com.inkdrop.app.domain.models.Room;

public interface RoomRepository extends CrudRepository<Room, Long> {
	@Cacheable(cacheNames="roomByUid", key="#root.args[0]") Room findByUid(Integer uid);
	Room findByFullName(String name);
	List<Room> findByUidIn(List<Integer> uids);
}
