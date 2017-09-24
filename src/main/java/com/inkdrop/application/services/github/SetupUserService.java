package com.inkdrop.application.services.github;

import com.inkdrop.application.commands.RepositoryCommand;
import com.inkdrop.domain.user.User;
import java.io.IOException;
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
  RepositoryCommand repositoryCommand;

  @Transactional
  public User setupUser(User appUser, GHMyself githubUser) throws IOException {
    log.info("Creating rooms...");
    findOrCreateRooms(githubUser, appUser);

    findOrCreateRoomsForOrganizations(githubUser, appUser);
    log.info("Done");

    return appUser;
  }

  private void findOrCreateRoomsForOrganizations(GHMyself githubUser, User appUser) throws IOException {
    for (GHOrganization organization : githubUser.getAllOrganizations()) {
      log.info("GUESS WHAT: I'm {}", organization.getName());
      for (GHRepository repository : organization.getRepositories().values()) {
        log.info("Creating {}", repository.getFullName());
        createRoom(repository, organization.getLogin(), appUser);
      }
    }
  }

  private void findOrCreateRooms(GHMyself githubUser, User appUser) throws IOException {
    githubUser.getRepositories().values()
        .parallelStream()
        .forEach(repo -> {
          createRoom(repo, null, appUser);
        });
  }

  private void createRoom(GHRepository repository, String organization, User appUser) {
    log.info("Creating room for repo: {}", repository.getFullName());
    try {
      repositoryCommand.findOrCreateRoom(repository, organization, appUser);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
