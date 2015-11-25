package com.inkdrop.queues;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
public class ChatManager {

	@Autowired
	RabbitTemplate template;
	
	private Logger log = LogManager.getLogger(ChatManager.class);
	
	private RabbitAdmin admin;
	private ConnectionFactory cf;
	
	public void sendMessageToRoom(String message, String room){
		initializeConfigurations();
		
		if(!queueExists(room)) {
			log.info("Queue is null, creating!");
			createQueue(room);
		}
		
		template.convertAndSend(room, message);
	}

	private void initializeConfigurations() {
		cf = template.getConnectionFactory();
		admin = new RabbitAdmin(cf);
	}
	
	private void createQueue(String room) {
		Queue q = new Queue(room, false, true, true);
		DirectExchange exchange = new DirectExchange(room);
		
		admin.declareExchange(exchange);
		admin.declareQueue(q);
		
		admin.declareBinding(BindingBuilder.bind(q).to(exchange).with(room));
		addMessageListener(q);
	}

	private void addMessageListener(Queue q) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cf);
		
		container.setConnectionFactory(cf);
		container.setQueueNames(q.getName());
		container.setMessageListener(new MessageListenerAdapter(){
			@Override
			public void onMessage(Message message, Channel channel) throws Exception {
				log.debug(message);
				log.info("Got: "+ new String(message.getBody()));
				// TODO Send to a websocket
			}
		});
		
		container.start();
	}

	private boolean queueExists(String queue){
		return admin.getQueueProperties(queue) != null;
	}
}
