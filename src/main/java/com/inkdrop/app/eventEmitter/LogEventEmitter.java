package com.inkdrop.app.eventEmitter;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inkdrop.app.domain.models.EventType;
import com.inkdrop.app.domain.models.LogEvent;
import com.inkdrop.app.domain.models.Room;
import com.inkdrop.app.domain.models.User;

import reactor.core.Reactor;
import reactor.event.Event;

@Component
public class LogEventEmitter {
	
	@Autowired Reactor r;
	
	public void messageSent(User user, Room room){
		LogEvent l = new LogEvent(new Date(), EventType.MESSAGE_SENT.getKey(), user.getId(), room.getId());
		r.notify("event", Event.wrap(l));
	}
	
	public void login(User user){
		LogEvent l = new LogEvent(new Date(), EventType.LOGIN.getKey(), user.getId(), null);
		r.notify("event", Event.wrap(l));
	}
	
	public void joinRoom(User user, Room room){
		LogEvent l = new LogEvent(new Date(), EventType.JOIN_ROOM.getKey(), user.getId(), room.getId());
		r.notify("event", Event.wrap(l));
	}
	
	public void leaveRoom(User user, Room room){
		LogEvent l = new LogEvent(new Date(), EventType.LEAVE_ROOM.getKey(), user.getId(), room.getId());
		r.notify("event", Event.wrap(l));
	}
	
	public void enterRoom(User user, Room room){
		LogEvent l = new LogEvent(new Date(), EventType.ENTER_ROOM.getKey(), user.getId(), room.getId());
		r.notify("event", Event.wrap(l));
	}
	
	public void newUser(User user){
		LogEvent l = new LogEvent(new Date(), EventType.NEW_USER.getKey(), user.getId(), null);
		r.notify("event", Event.wrap(l));
	}
}