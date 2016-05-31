package com.inkdrop.app.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mixpanel.mixpanelapi.ClientDelivery;
import com.mixpanel.mixpanelapi.MessageBuilder;
import com.mixpanel.mixpanelapi.MixpanelAPI;

@Service
public class MixpanelAPIService {

	private Logger logger = LogManager.getLogger(MixpanelAPIService.class);
	
	@Value("${mixpanel.token}")
	String mixpanelToken;
	
	public void sendEvent(String id, String eventName){
		try{
			MessageBuilder messages = new MessageBuilder(mixpanelToken);
			JSONObject event = messages.event(id, eventName, null);
			
			ClientDelivery delivery = new ClientDelivery();
			delivery.addMessage(event);
			
			 new MixpanelAPI().deliver(delivery);
		} catch(Exception e){
			logger.error(e);
		}
		
	}
}
