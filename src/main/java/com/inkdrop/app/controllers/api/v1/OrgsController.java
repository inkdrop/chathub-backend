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
import com.inkdrop.app.domain.repositories.OrganizationRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@EnableAutoConfiguration
@Slf4j
public class OrgsController extends BasicController {
	
	@Autowired OrganizationRepository organizatioRepository;

	@RequestMapping(method = RequestMethod.GET, path="/v1/orgs/{login}")
	public ResponseEntity<?> getOrgInfo(@PathVariable String login, @RequestHeader("Auth-Token") String token){
		try{
			Organization org = organizatioRepository.findByLoginIgnoreCase(login);
			return createSuccessfulResponse(org);
		} catch(Exception e) {
			log.error(e.getLocalizedMessage());
			return createErrorResponse(e);
		}
	}
}
