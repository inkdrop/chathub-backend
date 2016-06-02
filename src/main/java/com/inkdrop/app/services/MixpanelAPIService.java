package com.inkdrop.app.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.mixpanel.mixpanelapi.ClientDelivery;
import com.mixpanel.mixpanelapi.MessageBuilder;
import com.mixpanel.mixpanelapi.MixpanelAPI;

@Service
public class MixpanelAPIService {

	private Logger logger = LogManager.getLogger(MixpanelAPIService.class);
	
	@Value("${mixpanel.token}")
	String mixpanelToken;

	private MessageBuilder mbuilder;
	
	public MixpanelAPIService() {
		mbuilder = new MessageBuilder(mixpanelToken);
	}
	
	public void sendEvent(JSONObject event){
		try{
			ClientDelivery delivery = new ClientDelivery();
			delivery.addMessage(event);
			
			 new MixpanelAPI().deliver(delivery);
		} catch(Exception e){
			e.printStackTrace();
			logger.error(e);
		}
	}
	
	public MessageBuilder getMessageBuilder() {
		return mbuilder;
	}
}
