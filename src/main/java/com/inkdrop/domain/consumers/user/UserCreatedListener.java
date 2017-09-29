package com.inkdrop.domain.consumers.user;

import com.inkdrop.domain.user.User;
import com.inkdrop.domain.user.events.UserCreatedEvent;
import com.inkdrop.domain.user.events.UserJoinedRoomEvent;
import com.inkdrop.domain.user.events.UserLeftRoomEvent;
import com.inkdrop.domain.user.readModel.ReadUser;
import com.inkdrop.infrastructure.repositories.readRepositories.ReadUsersRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Async
@Slf4j
public class UserCreatedListener {

  @Autowired
  ReadUsersRepository readUsersRepository;

  @EventListener
  public void accept(UserCreatedEvent event){
    User user = event.getUser();
    ReadUser ru = getReadUser(user);

    readUsersRepository.save(ru);
  }

  // TODO move to own listener
  @EventListener
  public void accept(UserJoinedRoomEvent event){
    User user = event.getUser();
    ReadUser ru = getReadUser(user);

    readUsersRepository.save(ru);
  }

  // TODO move to own listener
  @EventListener
  public void accept(UserLeftRoomEvent event){
    User user = event.getUser();
    ReadUser ru = getReadUser(user);

    readUsersRepository.save(ru);
  }

  private ReadUser getReadUser(User user) {
    ReadUser ru = readUsersRepository.findOne(user.getId());
    if(ru == null){
      ru = new ReadUser();
      ru.setId(user.getId());
    }
    ru.setAvatar(user.getAvatar());
    ru.setBackendAccessToken(user.getBackendAccessToken());
    ru.setCompany(user.getCompany());
    ru.setEmail(user.getEmail());
    ru.setFirebaseToken(user.getFirebaseToken());
    ru.setLocation(user.getLocation());
    ru.setLogin(user.getLogin());
    ru.setUid(user.getUid());
    ru.setName(user.getName());
    ru.setRooms(getSubscribedRooms(user));

    return ru;
  }

  private Integer[] getSubscribedRooms(User user) {
    List<Integer> ids = user.getSubscriptions().stream()
        .map(s -> s.getRoomId().intValue())
        .collect(Collectors.toList());

    Integer[] placeholder = new Integer[ids.size()];
    return ids.toArray(placeholder);
  }


}
