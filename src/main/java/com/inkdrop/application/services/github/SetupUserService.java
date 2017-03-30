package com.inkdrop.application.services.github;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inkdrop.domain.models.User;
import com.inkdrop.infrastructure.repositories.RoomRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SetupUserService {

  @Autowired
  OrganizationService organizationService;

  @Autowired
  RepositoryService repositoryService;

  @Autowired
  RoomRepository roomRepository;

  public void setupUser(User appUser, GHMyself githubUser) throws IOException {
    log.info("Creating organizations");
    findOrCreateOrganizations(githubUser);

    log.info("Creating rooms...");
    findOrCreateRooms(githubUser);

    log.info("Linking user to rooms...");
    linkUserToRooms(appUser, githubUser);

    log.info("Done: linking user to rooms");
  }

  private void linkUserToRooms(User user, GHMyself githubUser) throws IOException {
    Map<String, GHRepository> repositories = githubUser.getRepositories();
    List<Integer> userRepoUid = repositories
        .values().stream()
        .map(repo -> repo.getId()).collect(Collectors.toList());

    for (GHOrganization org : githubUser.getAllOrganizations()) {
      userRepoUid.addAll(org.getRepositories().values()
          .stream()
          .map(repo -> repo.getId())
          .collect(Collectors.toList()));
    }
    userRepoUid.parallelStream()
        .forEach(uid -> user.getRooms().add(roomRepository.findByUid(uid)));
    //		user.setRooms(roomRepository.findByUidIn(userRepoUid)
    //				.stream().collect(Collectors.toSet()));
  }

  private void findOrCreateOrganizations(GHMyself githubUser) throws IOException {
    githubUser.getAllOrganizations()
        .parallelStream()
        .forEach(org -> {
          try {
            organizationService.findOrCreateOrganization(org);
          } catch (IOException e) {
            throw new UncheckedIOException(e);
          }
        });
  }


  private void findOrCreateRooms(GHMyself githubUser) throws IOException {
    githubUser.getRepositories().values()
        .parallelStream()
        .forEach(repo -> {
          try {
            repositoryService.findOrCreateRoom(repo);
          } catch (IOException e) {
            throw new UncheckedIOException(e);
          }
        });
  }
}
