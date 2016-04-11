package com.inkdrop.app.domain.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.inkdrop.app.domain.models.Message;
import com.inkdrop.app.domain.models.Room;

public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
	Page<Message> findByRoom(Room room, Pageable pageable);
//	List<Message> findLast10ByRoomOrderByIdAsc(Room room);
	Integer countByRoom(Room room);
}
