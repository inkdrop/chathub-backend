package com.inkdrop.app.eventnotifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inkdrop.app.consumers.EventConsumer;
import com.inkdrop.app.domain.builder.MixpanelEventBuilder;
import com.inkdrop.app.domain.models.EventType;
import com.inkdrop.app.domain.models.Message;
import com.inkdrop.app.domain.models.User;
import com.inkdrop.app.services.MixpanelAPIService;

import reactor.core.Reactor;
import reactor.event.Event;

@Component
public class EventNotifier {
	
	@Autowired Reactor r;
	
	@Autowired MixpanelAPIService mixpanelAPIService;
	
	public void messageSaved(Message m){
		r.notify(EventConsumer.MESSAGE_SAVED, Event.wrap(m));
		r.notify(EventConsumer.EVENT, Event.wrap(
				MixpanelEventBuilder
				.newEvent(mixpanelAPIService.getMessageBuilder())
				.ofType(EventType.MESSAGE_SENT)
				.withDistinctId(m.getUid())
				.build()));
	}
	
	public void newUser(User user){
	}
}