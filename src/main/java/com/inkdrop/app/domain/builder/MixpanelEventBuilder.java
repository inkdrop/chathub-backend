package com.inkdrop.app.domain.builder;

import java.util.Map;

import javax.annotation.Nonnull;

import org.json.JSONObject;

import com.inkdrop.app.domain.models.EventType;
import com.mixpanel.mixpanelapi.MessageBuilder;

public class MixpanelEventBuilder {

	private EventType type;
	private MessageBuilder messageBuilder;
	private String id;
	private Map<String, String> props;

	private MixpanelEventBuilder(MessageBuilder messageBuilder) {
		this.messageBuilder = messageBuilder;
	}
	public static MixpanelEventBuilder newEvent(MessageBuilder messageBuilder){
		return new MixpanelEventBuilder(messageBuilder);
	}

	public MixpanelEventBuilder ofType(@Nonnull EventType type){
		this.type = type;
		return this;
	}

	public MixpanelEventBuilder withDistinctId(@Nonnull String id){
		this.id = id;
		return this;
	}

	public MixpanelEventBuilder andProperties(Map<String, String> props){
		this.props = props;
		return this;
	}

	public JSONObject build(){
		return messageBuilder.event(id, type.getKey(), this.props != null ? new JSONObject(props) : null);
	}
}
