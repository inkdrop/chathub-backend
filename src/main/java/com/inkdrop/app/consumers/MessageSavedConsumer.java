package com.inkdrop.app.consumers;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inkdrop.app.domain.builder.MixpanelEventBuilder;
import com.inkdrop.app.domain.models.EventType;
import com.inkdrop.app.domain.models.Message;
import com.inkdrop.app.services.MixpanelAPIService;
import com.mixpanel.mixpanelapi.MessageBuilder;

import lombok.extern.slf4j.Slf4j;
import reactor.bus.Event;
import reactor.spring.context.annotation.Consumer;
import reactor.spring.context.annotation.Selector;

@Consumer
@Slf4j
public class MessageSavedConsumer {

	@Autowired MixpanelAPIService mixpanelApi;

	@Autowired MessageBuilder mbuilder;

	@Selector(value=EventConsumer.MESSAGE_SAVED, eventBus="@reactor")
	public void saveMessage(Event<Message> event){
		log.info("Pushing to firebase");
		pushToFirebase(event.getData());
		log.info("Pushed");
		mixpanelApi.sendEvent(getMixpanelJson((event.getData())));
	}

	private JSONObject getMixpanelJson(Message m){
		return MixpanelEventBuilder
				.newEvent(mbuilder)
				.ofType(EventType.MESSAGE_SENT)
				.withDistinctId(m.getUid())
				.andProperties(getMessageProperties(m))
				.build();
	}

	private DatabaseReference getDatabase(Message message) {
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference db = database.getReference("/messages");
		return db.child(message.getRoom().getUid().toString());
	}

	private void pushToFirebase(Message message){
		try{
			DatabaseReference db = getDatabase(message);
			db.child(message.getId().toString()).setValue(new ObjectMapper().writeValueAsString(message));
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	private Map<String, String> getMessageProperties(Message m){
		Map<String, String> map = new HashMap<>();
		map.put("sender_id", m.getSender().getId().toString());
		map.put("room_id", m.getRoom().getId().toString());
		return map;
	}
}
