package com.inkdrop.domain.room.factory;

import com.inkdrop.domain.room.Room;
import com.inkdrop.infrastructure.annotations.Command;
import com.inkdrop.infrastructure.repositories.RoomRepository;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Command
@Slf4j
public class RepositoryFactory {

  @Autowired
  RoomRepository roomRepository;

  public Room findOrCreateRoom(GHRepository repo) throws IOException {
    Room room = roomRepository.findByFullName(repo.getFullName());
    if (room == null) {
      room = new Room();
    }
    room = loadFromGithub(room, repo);
    return roomRepository.save(room);
  }

  private Room loadFromGithub(Room room, GHRepository repo) throws IOException {
    room.setPrivateRoom(repo.isPrivate());
    room.setCreatedAt(repo.getCreatedAt());
    room.setDescription(repo.getDescription());
    room.setHomepage(repo.getHomepage());
    room.setFullName(repo.getFullName());
    room.setName(repo.getName());
    room.setUid(repo.getId());
    room.setOwner(repo.getOwner().getLogin());
    room.setUpdatedAt(repo.getUpdatedAt());
    room.setOrganization("");
    room.setAvatar(repo.getOwner().getAvatarUrl());

    return room;
  }
}
