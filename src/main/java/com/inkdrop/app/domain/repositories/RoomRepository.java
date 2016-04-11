package com.inkdrop.app.domain.repositories;

import org.springframework.data.repository.CrudRepository;

import com.inkdrop.app.domain.models.Room;

public interface RoomRepository extends CrudRepository<Room, Long> {
	Room findByUid(Integer uid);
}
