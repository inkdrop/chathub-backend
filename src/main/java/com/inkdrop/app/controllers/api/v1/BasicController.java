package com.inkdrop.app.controllers.api.v1;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inkdrop.app.domain.models.User;
import com.inkdrop.app.domain.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BasicController {

	static class Params implements Serializable {
		private static final long serialVersionUID = 6961393145500932303L;
		
		private String content;
		
		public Params() {}
		
		@JsonCreator
		public Params(@JsonProperty("content") String content) {
			super();
			this.content = content;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
	}
	
	protected boolean isValid(Object object){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
      Validator validator = factory.getValidator();
      return validator.validate(object).isEmpty();
	}
	
	protected Object excludeFieldsFromObject(Object object, String[] toBeExcluded){
		try {
			for (String string : toBeExcluded) {
				Field f = object.getClass().getDeclaredField(string);
				if(f == null)
					continue;
				
				f.setAccessible(true);
				f.set(object, null);
			}
			return object;
		}catch(Exception e){
			log.error("Could not exclude fields", e);
			return null;
		}
	}
	
	protected ResponseEntity<Object> createSuccessfulResponse(Object response){
		return ResponseEntity.ok(response);
	}
	
	protected ResponseEntity<Object> createSuccessfulResponse(Object response, HttpStatus status){
		return new ResponseEntity<>(response, status);
	}
	
	protected ResponseEntity<String> createErrorResponse(Exception response){
		return createErrorResponse(response, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	protected ResponseEntity<String> createErrorResponse(Exception response, HttpStatus status){
		return new ResponseEntity<>(exception(response), status);
	}
	
	protected User findByBackendToken(String token, UserRepository userRepository){
		return userRepository.findByBackendAccessToken(token);
	}
	
	private String exception(Exception e){
		return "{\"error\": \""+e.getMessage()+"\" }";
	}
}
