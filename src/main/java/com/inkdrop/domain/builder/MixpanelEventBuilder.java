package com.inkdrop.domain.builder;

import com.inkdrop.domain.models.EventType;
import com.mixpanel.mixpanelapi.MessageBuilder;
import java.util.Map;
import lombok.Builder;
import org.json.JSONObject;

@Builder
public class MixpanelEventBuilder {

  private EventType type;
  private String id;
  private Map<String, String> properties;
  private String mixpanelToken;

  public JSONObject toJsonObject() {
    return new MessageBuilder(mixpanelToken)
        .event(id, type.getKey(), this.properties != null ? new JSONObject(properties) : null);
  }
}
