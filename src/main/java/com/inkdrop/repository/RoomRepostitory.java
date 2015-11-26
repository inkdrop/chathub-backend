package com.inkdrop.repository;

import org.springframework.data.repository.CrudRepository;

import com.inkdrop.model.Room;

public interface RoomRepostitory extends CrudRepository<Room, Long> {

}
