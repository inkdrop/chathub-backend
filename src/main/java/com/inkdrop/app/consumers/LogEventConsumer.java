package com.inkdrop.app.consumers;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inkdrop.app.domain.models.LogEvent;
import com.inkdrop.app.domain.repositories.LogEventRepository;

import reactor.core.Reactor;
import reactor.event.Event;
import reactor.event.selector.Selectors;
import reactor.function.Consumer;

@Component
public class LogEventConsumer {

	@Autowired Reactor r;
	
	@Autowired LogEventRepository logEventRepo;
	
	@PostConstruct
	public void onStartUp() {
		r.on(Selectors.R("event"), saveEvent());
	}

	private Consumer<Event<LogEvent>> saveEvent() {
		return event -> save(event);
	}

	private void save(Event<LogEvent> event) {
		logEventRepo.save(event.getData());
	}
}
