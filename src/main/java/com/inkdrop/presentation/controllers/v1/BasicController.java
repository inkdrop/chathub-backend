package com.inkdrop.presentation.controllers.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inkdrop.domain.user.User;
import com.inkdrop.infrastructure.repositories.UserRepository;
import java.io.Serializable;
import java.lang.reflect.Field;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class BasicController {

  protected Object excludeFieldsFromObject(Object object, String[] ignoredFields) {
    try {
      for (String string : ignoredFields) {
        Field f = object.getClass().getDeclaredField(string);
        if (f == null) {
          continue;
        }

        f.setAccessible(true);
        f.set(object, null);
      }
      return object;
    } catch (Exception e) {
      log.error("Could not exclude fields", e);
      return null;
    }
  }

  protected ResponseEntity<Object> createSuccessfulResponse(Object response) {
    return createSuccessfulResponse(response, HttpStatus.OK);
  }

  protected ResponseEntity<Object> createSuccessfulResponse(Object response, HttpStatus status) {
    return new ResponseEntity<>(response, status);
  }

  protected ResponseEntity<String> createErrorResponse(Exception response) {
    return createErrorResponse(response, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  protected ResponseEntity<String> createErrorResponse(Exception response, HttpStatus status) {
    return new ResponseEntity<>(exception(response), status);
  }

  protected User findByBackendToken(String token, UserRepository userRepository) {
    return userRepository.findByBackendAccessToken(token);
  }

  private String exception(Exception e) {
    return "{\"error\": \"" + e.getMessage() + "\" }";
  }

  @Data
  static class Params implements Serializable {

    private static final long serialVersionUID = 1L;
    private String content;

    @JsonCreator
    public Params(@JsonProperty("content") String content) {
      super();
      this.content = content;
    }
  }
}
