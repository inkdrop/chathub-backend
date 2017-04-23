package com.inkdrop.application.reactive.eventnotifier;

import com.inkdrop.application.reactive.consumers.MessageSavedConsumer;
import com.inkdrop.domain.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class EventNotifier {

  @Autowired
  MessageSavedConsumer consumer;

  public void messageSaved(Message m) {
    Mono.just(m)
        .publishOn(Schedulers.parallel())
        .subscribe(consumer);
  }
}
