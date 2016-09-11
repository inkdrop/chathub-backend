package com.inkdrop.app.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.inkdrop.app.domain.models.Message;
import com.inkdrop.app.services.MessageService;

import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.spring.context.annotation.Consumer;
import reactor.spring.context.annotation.Selector;

@Consumer
public class NewMessageConsumer {

	@Autowired MessageService messageService;
	
	@Autowired @Qualifier("webServiceReactor") EventBus bus;
	
	@Selector(value=EventConsumer.NEW_MESSAGE, eventBus="@persistenceReactor")
	public void saveMessage(Event<Message> event){
		Message m = messageService.save(event.getData());
		bus.notify(EventConsumer.MESSAGE_SAVED, Event.wrap(m));
	}
}
