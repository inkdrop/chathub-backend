package com.inkdrop.infrastructure.externalServices.firebase;

import com.google.common.collect.Maps;
import com.google.firebase.auth.FirebaseAuth;
import com.inkdrop.domain.user.User;
import org.springframework.stereotype.Service;

@Service
public class FirebaseTokenGenerator {

  public String getToken(User user) {
    return FirebaseAuth.getInstance()
        .createCustomToken(user.getUid().toString(), Maps.newHashMap());
  }
}
