package com.inkdrop.app.eventnotifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	
	@Autowired @Qualifier("persistenceReactor") EventBus r;
	
	@Autowired MessageBuilder mbuilder;
	
	public void saveMessage(Message message){
        r.notify(EventConsumer.NEW_MESSAGE, Event.wrap(message));
    }
	
	public void newUser(User user){
		r.notify(EventConsumer.EVENT, Event.wrap(
				MixpanelEventBuilder
				.newEvent(mbuilder)
				.ofType(EventType.NEW_USER)
				.withDistinctId(user.getUid().toString())
//				.andProperties(getProperties(m))
				.build()));
	}
	
}
