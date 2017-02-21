package com.inkdrop.app.consumers;

import com.inkdrop.app.commands.PushToFirebaseCommand;
import com.inkdrop.app.domain.builder.MixpanelEventBuilder;
import com.inkdrop.app.domain.models.EventType;
import com.inkdrop.app.domain.models.Message;
import com.inkdrop.app.services.MixpanelAPIService;
import com.mixpanel.mixpanelapi.MessageBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.bus.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class MessageSavedConsumer implements Consumer<Event<Message>> {

	@Autowired MixpanelAPIService mixpanelApi;

	@Autowired MessageBuilder mBuilder;

//	@Selector(value=EventConsumer.MESSAGE_SAVED, eventBus="@webServiceReactor")
	public void accept(Event<Message> event){
		new PushToFirebaseCommand().pushToFirebase(event.getData());
		mixpanelApi.sendEvent(getMixpanelJson((event.getData())));
	}

	private JSONObject getMixpanelJson(Message m){
		return MixpanelEventBuilder
				.newEvent(mBuilder)
				.ofType(EventType.MESSAGE_SENT)
				.withDistinctId(m.getUid())
				.andProperties(getMessageProperties(m))
				.build();
	}

	private Map<String, String> getMessageProperties(Message m){
		Map<String, String> map = new HashMap<>();
		map.put("sender_id", m.getSender().getId().toString());
		map.put("room_id", m.getRoom().getId().toString());
		return map;
	}
}
