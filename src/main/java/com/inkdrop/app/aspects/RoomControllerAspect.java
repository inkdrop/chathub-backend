package com.inkdrop.app.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inkdrop.app.domain.models.Room;
import com.inkdrop.app.domain.models.User;
import com.inkdrop.app.domain.repositories.RoomRepository;
import com.inkdrop.app.domain.repositories.UserRepository;
import com.inkdrop.app.exceptions.ChathubBackendException;
import com.inkdrop.app.helpers.InstantHelper;
import com.inkdrop.app.services.GitHubService;

@Aspect
@Component
public class RoomControllerAspect {
	private Logger logger = Logger.getLogger(RoomControllerAspect.class);

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	GitHubService githubService;

	@Pointcut("execution(* getRoomInformation*(..))")
	public void getRoomInformation() {}

	@Before("com.inkdrop.app.aspects.RoomControllerAspect.getRoomInformation()")
	public void checkRoomExists(JoinPoint joinPoint) throws ChathubBackendException{
		String roomName = (String) joinPoint.getArgs()[0];
		String token = (String) joinPoint.getArgs()[1];

		Room room = roomRepository.findByLoginIgnoreCase(roomName);
		String accessToken = getUserByBackendToken(token).getAccessToken();

		if(room == null || InstantHelper.biggerThanSixHours(room.getUpdatedAt())) {
			logger.info("Room needs to be created or updated");
			githubService.createOrUpdateRoom(roomName, accessToken);
		}
	}

	private User getUserByBackendToken(String token) {
		return userRepository.findByBackendAccessToken(token);
	}
}
