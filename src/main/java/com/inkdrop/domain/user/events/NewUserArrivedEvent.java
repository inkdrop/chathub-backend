package com.inkdrop.domain.user.events;

import com.inkdrop.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kohsuke.github.GHUser;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserArrivedEvent {

  private User user;
  private GHUser gitHubUser;
}
