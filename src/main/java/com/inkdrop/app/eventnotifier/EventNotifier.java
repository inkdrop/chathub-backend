package com.inkdrop.app.eventnotifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inkdrop.app.consumers.EventConsumer;
import com.inkdrop.app.domain.builder.MixpanelEventBuilder;
import com.inkdrop.app.domain.models.EventType;
import com.inkdrop.app.domain.models.Message;
import com.inkdrop.app.domain.models.User;
import com.mixpanel.mixpanelapi.MessageBuilder;

import reactor.bus.Event;
import reactor.bus.EventBus;

@Component
public class EventNotifier {
	
	@Autowired EventBus bus;
	
	@Autowired MessageBuilder mbuilder;
	
	public void newUser(User user){
		bus.notify(EventConsumer.EVENT, Event.wrap(
				MixpanelEventBuilder
				.newEvent(mbuilder)
				.ofType(EventType.NEW_USER)
				.withDistinctId(user.getUid().toString())
//				.andProperties(getProperties(m))
				.build()));
	}

	public void messageSaved(Message m) {
		bus.notify(EventConsumer.MESSAGE_SAVED, Event.wrap(m));
	}
	
}
