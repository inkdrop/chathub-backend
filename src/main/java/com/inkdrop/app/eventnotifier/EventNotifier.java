package com.inkdrop.app.eventnotifier;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inkdrop.app.consumers.EventConsumer;
import com.inkdrop.app.domain.models.EventType;
import com.inkdrop.app.domain.models.LogEvent;
import com.inkdrop.app.domain.models.Message;
import com.inkdrop.app.domain.models.Room;
import com.inkdrop.app.domain.models.User;

import reactor.core.Reactor;
import reactor.event.Event;

@Component
public class EventNotifier {
	
	@Autowired Reactor r;
	
	public void messageSaved(Message m){
		LogEvent l = new LogEvent(new Date(), EventType.MESSAGE_SENT.getKey(), m.getSender().getId(), m.getRoom().getId());
		r.notify(EventConsumer.EVENT, Event.wrap(l));
		r.notify(EventConsumer.MESSAGE_SAVED, Event.wrap(m));
	}
	
	public void login(User user){
		LogEvent l = new LogEvent(new Date(), EventType.LOGIN.getKey(), user.getId(), null);
		r.notify(EventConsumer.EVENT, Event.wrap(l));
	}
	
	public void joinRoom(User user, Room room){
		LogEvent l = new LogEvent(new Date(), EventType.JOIN_ROOM.getKey(), user.getId(), room.getId());
		r.notify(EventConsumer.EVENT, Event.wrap(l));
	}
	
	public void leaveRoom(User user, Room room){
		LogEvent l = new LogEvent(new Date(), EventType.LEAVE_ROOM.getKey(), user.getId(), room.getId());
		r.notify(EventConsumer.EVENT, Event.wrap(l));
	}
	
	public void enterRoom(User user, Room room){
		LogEvent l = new LogEvent(new Date(), EventType.ENTER_ROOM.getKey(), user.getId(), room.getId());
		r.notify(EventConsumer.EVENT, Event.wrap(l));
	}
	
	public void newUser(User user){
		LogEvent l = new LogEvent(new Date(), EventType.NEW_USER.getKey(), user.getId(), null);
		r.notify(EventConsumer.EVENT, Event.wrap(l));
	}
}