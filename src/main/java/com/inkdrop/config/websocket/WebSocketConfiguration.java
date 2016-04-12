package com.inkdrop.config.websocket;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration 
extends AbstractWebSocketMessageBrokerConfigurer
implements ApplicationListener<SessionConnectedEvent> {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// the endpoint for websocket connections
		registry.addEndpoint("/stomp")
		.setAllowedOrigins("http://127.0.0.1:3000", "http://usechathub.herokuapp.com")
		.addInterceptors(new HttpSessionHandshakeInterceptor())
		.withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/");
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void onApplicationEvent(SessionConnectedEvent event) {
		// TODO find a way to track online/offline users
	}

}
