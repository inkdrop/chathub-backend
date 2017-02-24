package com.inkdrop.app.consumers;

import com.inkdrop.app.commands.PushToFirebaseCommand;
import com.inkdrop.app.domain.builder.MixpanelEventBuilder;
import com.inkdrop.app.domain.models.EventType;
import com.inkdrop.app.domain.models.Message;
import com.inkdrop.app.services.MixpanelAPIService;
import com.mixpanel.mixpanelapi.MessageBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageSavedConsumer implements Consumer<Message> {

  @Autowired
  MixpanelAPIService mixpanelApi;

  @Autowired
  MessageBuilder mBuilder;

  public void accept(Message message) {
    new PushToFirebaseCommand().pushToFirebase(message);
    mixpanelApi.sendEvent(getMixpanelJson((message)));
  }

  private JSONObject getMixpanelJson(Message m) {
    return MixpanelEventBuilder
        .newEvent(mBuilder)
        .ofType(EventType.MESSAGE_SENT)
        .withDistinctId(m.getUid())
        .andProperties(getMessageProperties(m))
        .build();
  }

  private Map<String, String> getMessageProperties(Message m) {
    Map<String, String> map = new HashMap<>();
    map.put("sender_id", m.getSender().getId().toString());
    map.put("room_id", m.getRoom().getId().toString());
    return map;
  }
}
