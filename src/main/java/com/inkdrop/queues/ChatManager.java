package com.inkdrop.queues;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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

	private static final String ROOM_TOPIC_EXCHANGE = "room-topic-exchange";
//	private static final String ROOM_DIRECT_EXCHANGE = "room-direct-exchange";

	@Autowired
	RabbitTemplate template;
	
	private Logger log = LogManager.getLogger(ChatManager.class);
	
	private RabbitAdmin admin;
	private ConnectionFactory cf;
	
	public void sendMessageToRoom(String message, String room){
		initializeConfigurations();
		
		String rid = getRoomId(room);
		if(!queueExists(rid)) {
			log.info("Queue is null, creating!");
			createQueue(rid);
		}
		
		template.convertAndSend(ROOM_TOPIC_EXCHANGE, rid, message);
	}
	
	public void sendToAllRooms(String message){
		template.convertAndSend(ROOM_TOPIC_EXCHANGE, "broadcast", message);
	}

	private void initializeConfigurations() {
		cf = template.getConnectionFactory();
		admin = new RabbitAdmin(cf);
	}
	
	private void createQueue(String roomId) {
		Queue q = new Queue(roomId, false, false, true);
//		DirectExchange specificRoom = new DirectExchange(ROOM_DIRECT_EXCHANGE, false, true);
		TopicExchange allRooms = new TopicExchange(ROOM_TOPIC_EXCHANGE, false, true);
		
//		admin.declareExchange(specificRoom);
		admin.declareExchange(allRooms);
		
		admin.declareQueue(q);
		
//		admin.declareBinding(BindingBuilder.bind(q).to(specificRoom).with(roomId));
		admin.declareBinding(BindingBuilder.bind(q).to(allRooms).with(roomId));
		admin.declareBinding(BindingBuilder.bind(q).to(allRooms).with("broadcast"));
		
		addMessageListener(q);
	}

	private void addMessageListener(Queue q) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cf);
		
		container.setConnectionFactory(cf);
		container.setQueueNames(q.getName());
		container.setMessageListener(new MessageListenerAdapter(){
			@Override
			public void onMessage(Message message, Channel channel) throws Exception {
				log.info(message);
				log.info("Got: "+ new String(message.getBody()));
				// TODO Send to a websocket
			}
		});
		
		 if (!container.isRunning()) {
             container.start();
         }	}
	
	private String getRoomId(String room){
		return "room."+room;
	}

	private boolean queueExists(String queue){
		return admin.getQueueProperties(queue) != null;
	}
}
