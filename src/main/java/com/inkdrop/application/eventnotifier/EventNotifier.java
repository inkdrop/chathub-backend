package com.inkdrop.application.eventnotifier;

import com.inkdrop.application.consumers.MessageSavedConsumer;
import com.inkdrop.domain.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class EventNotifier {

  @Autowired
  MessageSavedConsumer consumer;

//
//	public void newUser(User user){
//		bus.notify(EventConsumer.EVENT, Event.wrap(
//				MixpanelEventBuilder
//				.newEvent(mbuilder)
//				.ofType(EventType.NEW_USER)
//				.withDistinctId(user.getUid().toString())
////				.andProperties(getProperties(m))
//				.build()));
//	}

  public void messageSaved(Message m) {
    Mono.just(m)
        .publishOn(Schedulers.parallel())
        .subscribe(consumer);
  }

}
