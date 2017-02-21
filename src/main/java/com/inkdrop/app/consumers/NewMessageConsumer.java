package com.inkdrop.app.consumers;

import com.inkdrop.app.domain.models.Message;
import com.inkdrop.app.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.util.function.Consumer;

public class NewMessageConsumer implements Consumer<Event<Message>>{

	@Autowired MessageService messageService;
	
	@Autowired @Qualifier("webServiceReactor")
	EventBus bus;
	
	public void accept(Event<Message> event){
		Message m = messageService.save(event.getData());
		bus.notify(EventConsumer.MESSAGE_SAVED, Event.wrap(m));
	}
}
