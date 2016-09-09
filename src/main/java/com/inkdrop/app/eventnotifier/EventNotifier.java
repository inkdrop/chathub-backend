package com.inkdrop.app.eventnotifier;

import java.util.HashMap;
import java.util.Map;

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
	
	@Autowired EventBus r;
	
	@Autowired MessageBuilder mbuilder;
	
	public void saveMessage(Message message){
        r.notify(EventConsumer.NEW_MESSAGE, Event.wrap(message));
    }
	
	public void messageSaved(Message m){
		r.notify(EventConsumer.MESSAGE_SAVED, Event.wrap(m));
		r.notify(EventConsumer.EVENT, Event.wrap(
				MixpanelEventBuilder
				.newEvent(mbuilder)
				.ofType(EventType.MESSAGE_SENT)
				.withDistinctId(m.getUid())
				.andProperties(getMessageProperties(m))
				.build()));
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
	
	private Map<String, String> getMessageProperties(Message m){
		Map<String, String> map = new HashMap<>();
		map.put("sender_id", m.getSender().getId().toString());
		map.put("room_id", m.getRoom().getId().toString());
		
		return map;
	}
}
