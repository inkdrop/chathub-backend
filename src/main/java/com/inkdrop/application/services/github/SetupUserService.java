package com.inkdrop.application.services.github;

import com.inkdrop.application.commands.OrganizationCommand;
import com.inkdrop.application.commands.RepositoryCommand;
import com.inkdrop.domain.user.User;
import com.inkdrop.infrastructure.repositories.RoomRepository;
import com.inkdrop.infrastructure.repositories.UserRepository;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class SetupUserService {

  @Autowired
  OrganizationCommand organizationCommand;

  @Autowired
  RepositoryCommand repositoryCommand;

  @Autowired
  RoomRepository roomRepository;

  @Autowired
  UserRepository userRepository;

  @Transactional
  public User setupUser(User appUser, GHMyself githubUser) throws IOException {
    log.info("Creating organizations");
    findOrCreateOrganizations(githubUser);

    log.info("Creating rooms...");
    findOrCreateRooms(githubUser);

    log.info("Linking user to rooms...");
    linkUserToRooms(appUser, githubUser);

    log.info("Done: linking user to rooms");
    return userRepository.save(appUser);
  }

  private void linkUserToRooms(User user, GHMyself githubUser) throws IOException {
    Map<String, GHRepository> repositories = githubUser.getRepositories();
    List<Integer> userRepoUid = repositories
        .values().stream()
        .map(GHRepository::getId).collect(Collectors.toList());

    for (GHOrganization org : githubUser.getAllOrganizations()) {
      userRepoUid.addAll(org.getRepositories().values()
          .stream()
          .map(GHRepository::getId)
          .collect(Collectors.toList()));
    }
    userRepoUid.parallelStream()
        .forEach(uid -> user.getRooms().add(roomRepository.findByUid(uid)));
  }

  private void findOrCreateOrganizations(GHMyself githubUser) throws IOException {
    githubUser.getAllOrganizations()
        .parallelStream()
        .forEach(org -> {
          try {
            organizationCommand.findOrCreateOrganization(org);
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
            repositoryCommand.findOrCreateRoom(repo);
          } catch (IOException e) {
            throw new UncheckedIOException(e);
          }
        });
  }
}
