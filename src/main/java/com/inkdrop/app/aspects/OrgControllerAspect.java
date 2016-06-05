package com.inkdrop.app.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inkdrop.app.domain.models.Organization;
import com.inkdrop.app.domain.models.User;
import com.inkdrop.app.domain.repositories.OrganizationRepository;
import com.inkdrop.app.domain.repositories.UserRepository;
import com.inkdrop.app.exceptions.ChathubBackendException;
import com.inkdrop.app.helpers.InstantHelper;
import com.inkdrop.app.services.GitHubService;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class OrgControllerAspect {

	@Autowired
	OrganizationRepository orgRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	GitHubService githubService;

	@Pointcut("execution(* getOrgInfo*(..))")
	public void getOrgInfo() {
		//
	}

	@Before("com.inkdrop.app.aspects.OrgControllerAspect.getOrgInfo()")
	public void checkRoomExists(JoinPoint joinPoint) throws ChathubBackendException {
		String orgLogin = (String) joinPoint.getArgs()[0];
		String token = (String) joinPoint.getArgs()[1];

		Organization org = orgRepository.findByLoginIgnoreCase(orgLogin);
		String accessToken = getUserByBackendToken(token).getAccessToken();

		if(org == null || InstantHelper.biggerThanSixHours(org.getUpdatedAt())) {
			log.info("Org needs to be created or updated");
			githubService.createOrUpdateOrg(orgLogin, accessToken);
		}
	}

	private User getUserByBackendToken(String token) {
		return userRepository.findByBackendAccessToken(token);
	}
}
