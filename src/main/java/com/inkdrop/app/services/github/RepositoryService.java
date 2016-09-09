package com.inkdrop.app.services.github;

import java.io.IOException;

import org.kohsuke.github.GHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inkdrop.app.domain.models.Room;
import com.inkdrop.app.domain.repositories.OrganizationRepository;
import com.inkdrop.app.domain.repositories.RoomRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RepositoryService extends AbstractGithubService {

	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	OrganizationRepository organizationRepository;
	
	public Room findOrCreateRoom(GHRepository repo){
		try{
			Room room = roomRepository.findByFullName(repo.getFullName());
			if(room == null){
				log.info("New room being created: "+repo.getFullName());
				room = loadFromGithub(new Room(), repo);
			} else {
				log.info("Updating room data: "+repo.getFullName());
				room = loadFromGithub(room, repo);
			}
			room.setOrganization(organizationRepository.findByLoginIgnoreCase(repo.getOwner().getLogin()));
			return roomRepository.save(room);
		} catch(IOException e){
			e.printStackTrace();
			return null;
		}
		
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
