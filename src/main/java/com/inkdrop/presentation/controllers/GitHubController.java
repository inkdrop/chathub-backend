package com.inkdrop.presentation.controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.inkdrop.application.services.github.GitHubLoginService;
import com.inkdrop.domain.user.User;
import com.inkdrop.infrastructure.exceptions.ChathubBackendException;
import com.inkdrop.presentation.controllers.v1.BasicController;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitHubController extends BasicController {

  @Autowired
  GitHubLoginService githubLoginService;

  @RequestMapping(method = RequestMethod.POST, path = "/auth/github")
  public ResponseEntity createUser(@RequestParam("token") String token) {
    try {
      User user = githubLoginService.createOrFindUser(token);
      Assert.notNull(user, "No user was found");
      user.setFirebaseJwt(getFirebaseJwtToken(user.getUid()));
      return new ResponseEntity(user, HttpStatus.OK);
    } catch (ChathubBackendException e) {
      return createErrorResponse(e);
    }
  }

  private String getFirebaseJwtToken(Integer uid) {
    Map<String, Object> params = new HashMap<>();
    return FirebaseAuth.getInstance().createCustomToken(uid.toString(), params);
  }
}
