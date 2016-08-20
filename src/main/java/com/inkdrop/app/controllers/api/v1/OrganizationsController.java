package com.inkdrop.app.controllers.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inkdrop.app.domain.models.Organization;
import com.inkdrop.app.domain.models.User;
import com.inkdrop.app.domain.repositories.OrganizationRepository;
import com.inkdrop.app.domain.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@EnableAutoConfiguration
@Slf4j
public class OrganizationsController extends BasicController {

	@Autowired OrganizationRepository organizatioRepository;
	
	@Autowired
	UserRepository userRepository;

	@RequestMapping(method = RequestMethod.GET, path="/v1/organizations/{uid}")
	public ResponseEntity<?> getOrgInfo(@PathVariable String uid, @RequestHeader("Auth-Token") String token){
		try{
			log.info("Loading org: "+uid);
			Organization org = organizatioRepository.findByUid(Integer.parseInt(uid));
			return createSuccessfulResponse(org);
		} catch(Exception e) {
			log.error(e.getLocalizedMessage());
			return createErrorResponse(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, path="/v1/organizations")
	public ResponseEntity<?> getUserOrgs(@RequestHeader("Auth-Token") String token){
		try{
			User user = findByBackendToken(token, userRepository);
			return createSuccessfulResponse(organizatioRepository.findByMembers(user.getLogin()));
		} catch(Exception e) {
			log.error(e.getLocalizedMessage());
			return createErrorResponse(e);
		}
	}
}
