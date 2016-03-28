package com.inkdrop.app.reactor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.inkdrop.app.domain.formatter.FormatterFactory;
import com.inkdrop.app.domain.models.Message;

import reactor.core.Reactor;
import reactor.event.Event;
import reactor.event.selector.Selectors;
import reactor.function.Consumer;

@Component
public class MessageReactor {

	@Autowired
   private Reactor r;
	
	@Autowired
	private SimpMessagingTemplate webSocket;
	
	@PostConstruct
   public void onStartUp() {
       r.on(Selectors.R("message.created"), pushToWebsocket());
   }

	private Consumer<Event<Message>> pushToWebsocket() {
		return messageEvent -> send(messageEvent);
	}

	private void send(Event<Message> messageEvent) {
		Message message = messageEvent.getData();
		String json = FormatterFactory.getFormatter(Message.class).toJson(message);
		webSocket.convertAndSend(channel(message), json);
	}

	private String channel(Message message) {
		return "/room.".concat(message.getRoom().getUid().toString());
	}
}
