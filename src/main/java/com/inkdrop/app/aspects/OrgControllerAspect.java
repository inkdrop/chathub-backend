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
import com.inkdrop.app.services.github.OrganizationService;

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
	OrganizationService organizationService;

	@Pointcut("execution(* getOrgInfo*(..))")
	public void getOrgInfo() {}

	@Before("com.inkdrop.app.aspects.OrgControllerAspect.getOrgInfo()")
	public void checkRoomExists(JoinPoint joinPoint) throws ChathubBackendException {
//		Integer uid = (Integer) joinPoint.getArgs()[0];
//		String backendToken = (String) joinPoint.getArgs()[1];
//
//		Organization org = orgRepository.findByUid(uid);
//		String accessToken = getUserByBackendToken(backendToken).getAccessToken();
//
//		if(org == null || InstantHelper.biggerThanSixHours(org.getUpdatedAt())) {
//			log.info("Organization needs to be created or updated");
//			organizationService.findOrCreateOrganizationByName(accessToken, login);
//		}
	}

	private User getUserByBackendToken(String token) {
		return userRepository.findByBackendAccessToken(token);
	}
}
