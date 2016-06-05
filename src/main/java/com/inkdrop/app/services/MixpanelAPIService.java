package com.inkdrop.app.services;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.mixpanel.mixpanelapi.ClientDelivery;
import com.mixpanel.mixpanelapi.MixpanelAPI;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MixpanelAPIService {

	public void sendEvent(JSONObject event){
		try{
			ClientDelivery delivery = new ClientDelivery();
			delivery.addMessage(event);
			
			 new MixpanelAPI().deliver(delivery);
		} catch(Exception e){
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
		}
	}
}
