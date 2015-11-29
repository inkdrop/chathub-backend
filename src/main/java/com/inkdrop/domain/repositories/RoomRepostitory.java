package com.inkdrop.domain.repositories;

import org.springframework.data.repository.CrudRepository;

import com.inkdrop.domain.models.Room;

public interface RoomRepostitory extends CrudRepository<Room, Long> {
	public Room findByUid(Integer uid);
}
