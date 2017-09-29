package com.inkdrop.domain.consumers.user;

import com.inkdrop.domain.room.Room;
import com.inkdrop.domain.room.factory.RepositoryFactory;
import com.inkdrop.domain.user.User;
import com.inkdrop.domain.user.events.NewUserArrivedEvent;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NewUserArrivedListener {

  @Autowired
  RepositoryFactory repositoryFactory;

  @EventListener
  public void userCreated(NewUserArrivedEvent event) {
    try {
      User user = event.getUser();
      GHUser ghUser = event.getGitHubUser();

      createRoomsAndSubscribe(user, ghUser);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void createRoomsAndSubscribe(User user, GHUser ghUser) throws IOException {
    ghUser.getRepositories().values()
        .parallelStream()
        .forEach(repo -> createRoomAndSubscribe(repo, user));
  }

  private void createRoomAndSubscribe(GHRepository repo, User user) {
    try {
      log.info("Creating room for {}", repo.getFullName());
      Room room = repositoryFactory.findOrCreateRoom(repo);
      user.subscribeToRoom(room.getId());
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
