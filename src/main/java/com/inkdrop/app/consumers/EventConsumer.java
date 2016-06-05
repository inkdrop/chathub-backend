package com.inkdrop.app.consumers;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inkdrop.app.domain.models.Message;
import com.inkdrop.app.services.MixpanelAPIService;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.bus.selector.Selectors;
import reactor.fn.Consumer;

@Component
public class EventConsumer {

	public static final String MESSAGE_SAVED = "message_saved";
	public static final String EVENT = "event";

	@Autowired EventBus r;
	
	@Autowired MixpanelAPIService mixpanelApi;
	
	
	@PostConstruct
	public void onStartUp() {
		r.on(Selectors.R(MESSAGE_SAVED), createEventAndPush());
		r.on(Selectors.R(EVENT), pushToMixpanel());
	}

	public Consumer<Event<Message>> createEventAndPush() {
		return event -> push(event.getData());
	}
	
	public Consumer<Event<JSONObject>> pushToMixpanel() {
		return event -> mixpanelApi.sendEvent(event.getData());
	}
	
	private void push(Message m) {
		sendMessage(m);
	}

	private DatabaseReference getDatabase(Message message) {
		final FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference db = database.getReference("/messages");
		return db.child(message.getRoom().getUid().toString());
	}
	
	private void sendMessage(Message message){
		try{
			DatabaseReference db = getDatabase(message);
			db.child(message.getUid()).setValue(new ObjectMapper().writeValueAsString(message));
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
