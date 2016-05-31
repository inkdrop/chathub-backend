package com.inkdrop.app.controllers.api.v1;

import static com.monitorjbl.json.Match.match;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.inkdrop.app.domain.models.User;
import com.inkdrop.app.domain.repositories.UserRepository;
import com.monitorjbl.json.JsonResult;
import com.monitorjbl.json.JsonView;

class BasicController {

	static class Params implements Serializable {
		private static final long serialVersionUID = 6961393145500932303L;
		
		private String content;
		
		public Params() {
			// Default
		}
		
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
	
	protected Object jsonWithExclusions(Object object, String... fields) throws JsonProcessingException{
		JsonResult json = JsonResult.instance();
		return json.use(JsonView.with(object).onClass(object.getClass(), match().exclude(fields))).returnValue();
	}
	
	protected ResponseEntity<Object> createSuccessfulResponse(Object response){
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	protected ResponseEntity<String> createErrorResponse(Exception response){
		return new ResponseEntity<>(response.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	protected User findByBackendToken(String token, UserRepository userRepository){
		return userRepository.findByBackendAccessToken(token);
	}
	
	protected String exception(Exception e){
		return "{'error': '"+e.getMessage()+"' }";
	}
}
