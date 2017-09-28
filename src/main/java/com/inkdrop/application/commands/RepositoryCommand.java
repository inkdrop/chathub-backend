package com.inkdrop.application.commands;

import com.inkdrop.domain.room.Room;
import com.inkdrop.domain.user.User;
import com.inkdrop.infrastructure.annotations.Command;
import com.inkdrop.infrastructure.repositories.RoomRepository;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Command
@Slf4j
public class RepositoryCommand {

  @Autowired
  RoomRepository roomRepository;

  public Room findOrCreateRoom(GHRepository repo, String orgName, User appUser) throws IOException {
    Room room = roomRepository.findByFullName(repo.getFullName());
    if (room == null) {
      room = new Room();
      log.debug("New room being created: " + repo.getFullName());
    }
    room = loadFromGithub(room, repo, orgName);
//    room.addUser(appUser);

    roomRepository.save(room);
    return room;
  }

  private Room loadFromGithub(Room room, GHRepository repo, String orgName) throws IOException {
    room.setPrivateRoom(repo.isPrivate());
    room.setCreatedAt(repo.getCreatedAt());
    room.setDescription(repo.getDescription());
    room.setHomepage(repo.getHomepage());
    room.setFullName(repo.getFullName());
    room.setName(repo.getName());
    room.setUid(repo.getId());
    room.setOwner(repo.getOwner().getLogin());
    room.setUpdatedAt(repo.getUpdatedAt());
    room.setOrganization(orgName);
    room.setAvatar(repo.getOwner().getAvatarUrl());

    return room;
  }
}
