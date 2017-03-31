package com.inkdrop.application.commands;

import com.inkdrop.application.services.github.AbstractGitHubService;
import com.inkdrop.domain.models.Room;
import com.inkdrop.infrastructure.annotations.Command;
import com.inkdrop.infrastructure.repositories.OrganizationRepository;
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

  @Autowired
  OrganizationRepository organizationRepository;

  public Room findOrCreateRoom(GHRepository repo) throws IOException {
    Room room = roomRepository.findByFullName(repo.getFullName());
    if (room == null) {
      log.debug("New room being created: " + repo.getFullName());
      room = loadFromGithub(new Room(), repo);
      roomRepository.save(room);
    }
    return room;
  }

  private Room loadFromGithub(Room room, GHRepository repo) throws IOException {
    room.set_private(repo.isPrivate());
    room.setCreatedAt(repo.getCreatedAt());
    room.setDescription(repo.getDescription());
    room.setHomepage(repo.getHomepage());
    room.setFullName(repo.getFullName());
    room.setName(repo.getName());
    room.setUid(repo.getId());
    room.setOwner(repo.getOwner().getLogin());
    room.setUpdatedAt(repo.getUpdatedAt());
    room.setOrganization(organizationRepository.findByLoginIgnoreCase(repo.getOwnerName()));

    return room;
  }
}
