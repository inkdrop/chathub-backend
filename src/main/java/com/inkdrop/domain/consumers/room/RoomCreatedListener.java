package com.inkdrop.domain.consumers.room;

import com.inkdrop.domain.room.Room;
import com.inkdrop.domain.room.events.RoomCreatedEvent;
import com.inkdrop.domain.room.readModel.ReadRoom;
import com.inkdrop.infrastructure.repositories.readRepositories.ReadRoomsRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Async
public class RoomCreatedListener {

  @Autowired
  ReadRoomsRepository readRoomsRepository;

  @EventListener
  public void accept(RoomCreatedEvent event){
    Room room = event.getRoom();
    ReadRoom rr = getReadRoom(room);

    readRoomsRepository.save(rr);
  }

  @NotNull
  private ReadRoom getReadRoom(Room room) {
    ReadRoom rr = new ReadRoom();
    rr.setAvatar(room.getAvatar());
    rr.setCreatedAt(room.getCreatedAt());
    rr.setUpdatedAt(room.getUpdatedAt());
    rr.setDescription(room.getDescription());
    rr.setFullName(room.getFullName());
    rr.setHomepage(room.getHomepage());
    rr.setId(room.getId());
    rr.setName(room.getName());
    rr.setOrganization(room.getOrganization());
    rr.setOwner(room.getOwner());
    rr.setUid(room.getUid());
    return rr;
  }
}
