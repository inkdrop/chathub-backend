package com.inkdrop.app.consumers;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inkdrop.app.domain.formatter.FormatterFactory;
import com.inkdrop.app.domain.models.LogEvent;
import com.inkdrop.app.domain.models.Message;
import com.inkdrop.app.domain.repositories.LogEventRepository;

import reactor.core.Reactor;
import reactor.event.Event;
import reactor.event.selector.Selectors;
import reactor.function.Consumer;

@Component
public class EventConsumer {

	public static final String MESSAGE_SAVED = "message_saved";
	public static final String EVENT = "event";

	@Autowired Reactor r;
	
	@Autowired LogEventRepository logEventRepo;
	
	@PostConstruct
	public void onStartUp() {
		r.on(Selectors.R(EVENT), saveEvent());
		r.on(Selectors.R(MESSAGE_SAVED), pushToFirebase());
	}

	public Consumer<Event<Message>> pushToFirebase() {
		return event -> push(event.getData());
	}
	
	private Consumer<Event<LogEvent>> saveEvent() {
		return event -> saveEventToDatabase(event.getData());
	}

	private void push(Message data) {
		sendMessage(data);
	}

	private void saveEventToDatabase(LogEvent log) {
		logEventRepo.save(log);
	}
	
	private DatabaseReference getDatabase(Message message) {
		final FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference db = database.getReference("/messages");
		return db.child(message.getRoom().getFullName());
	}
	
	private void sendMessage(Message message){
		DatabaseReference db = getDatabase(message);
		db.child(message.getUid()).setValue(FormatterFactory.getFormatter(Message.class).toJson(message));
	}
}
