package com.inkdrop.app.domain.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.inkdrop.app.domain.models.Message;
import com.inkdrop.app.domain.models.Room;

public interface MessageRepository extends CrudRepository<Message, Long> {
	List<Message> findByRoom(Room room);
	List<Message> findLast10ByRoomOrderByIdAsc(Room room);
	Integer countByRoom(Room room);
}
