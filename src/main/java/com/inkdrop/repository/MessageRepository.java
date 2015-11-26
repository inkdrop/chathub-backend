package com.inkdrop.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.inkdrop.model.Message;
import com.inkdrop.model.Room;

public interface MessageRepository extends CrudRepository<Message, Long> {
	List<Message> findByRoom(Room room);
}
