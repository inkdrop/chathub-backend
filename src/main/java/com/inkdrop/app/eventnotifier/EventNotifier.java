package com.inkdrop.app.eventnotifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inkdrop.app.consumers.EventConsumer;
import com.inkdrop.app.domain.models.Message;
import com.inkdrop.app.domain.models.User;

import reactor.core.Reactor;
import reactor.event.Event;

@Component
public class EventNotifier {
	
	@Autowired Reactor r;
	
	public void messageSaved(Message m){
		r.notify(EventConsumer.MESSAGE_SAVED, Event.wrap(m));
	}
	
	public void newUser(User user){
	}
}