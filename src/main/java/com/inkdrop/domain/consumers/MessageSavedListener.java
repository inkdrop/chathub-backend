package com.inkdrop.domain.consumers;

import com.inkdrop.application.commands.PushToFirebaseCommand;
import com.inkdrop.application.services.MixpanelAPIService;
import com.inkdrop.domain.builder.MixpanelEventBuilder;
import com.inkdrop.domain.EventType;
import com.inkdrop.domain.message.Message;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageSavedListener {

  @Autowired
  MixpanelAPIService mixpanelApi;

  @Autowired
  PushToFirebaseCommand pushToFirebaseCommand;

  @Value("${mixpanel.token:invalid}")
  String mixpanelToken;

  @Async
  @EventListener
  public void accept(Message message) {
    pushToFirebaseCommand.pushToFirebase(message);
    mixpanelApi.sendEvent(getMixpanelJson((message)));
    log.info("Message posted");
  }

  private JSONObject getMixpanelJson(Message m) {
    return MixpanelEventBuilder.builder()
        .mixpanelToken(mixpanelToken)
        .type(EventType.MESSAGE_SENT)
        .id(m.getUid())
        .properties(getMessageProperties(m))
        .build()
        .toJsonObject();
  }

  private Map<String, String> getMessageProperties(Message m) {
    Map<String, String> map = new HashMap<>();
    map.put("sender_id", m.getSender().getId().toString());
    map.put("room_id", m.getRoom().getId().toString());
    return map;
  }
}
