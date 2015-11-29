package com.inkdrop.routers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.inkdrop.domain.models.Message;
import com.inkdrop.domain.models.Room;
import com.inkdrop.domain.presenters.MessagePresenter;
import com.rabbitmq.client.Channel;

@Component
public class RoomRouter {

	private static final String ROOM_DIRECT_EXCHANGE = "room-direct-exchange";
	private static final String ROOM_FANOUT_EXCHANGE = "room-fanout-exchange";

	@Autowired
	RabbitTemplate template;

	@Autowired
	private SimpMessagingTemplate webSocket;

	private Logger log = LogManager.getLogger(RoomRouter.class);

	private RabbitAdmin admin;
	private ConnectionFactory cf;

	public void sendMessageToRoom(Message message){
		initializeConfigurations();

		String rid = getRoomId(message.getRoom());
		if(!queueExists(rid)) {
			log.info("Queue is null, creating!");
			createQueue(rid);
		}

		String json = new MessagePresenter(message).toJson();
		template.convertAndSend(ROOM_DIRECT_EXCHANGE, rid, json);
	}

	public void sendToAllRooms(String message){
		template.convertAndSend(ROOM_FANOUT_EXCHANGE, "", message);
	}

	private void initializeConfigurations() {
		cf = template.getConnectionFactory();
		admin = new RabbitAdmin(cf);
	}

	private void createQueue(String roomId) {
		Queue q = new Queue(roomId, false, false, true);
		DirectExchange roomExchange = new DirectExchange(ROOM_DIRECT_EXCHANGE, false, true);
		FanoutExchange fanoutExchange = new FanoutExchange(ROOM_FANOUT_EXCHANGE, false, true);

		admin.declareExchange(roomExchange);
		admin.declareExchange(fanoutExchange);

		admin.declareQueue(q);

		admin.declareBinding(BindingBuilder.bind(q).to(roomExchange).with(roomId));
		admin.declareBinding(BindingBuilder.bind(q).to(fanoutExchange));

		addMessageListener(q);
	}

	private void addMessageListener(Queue q) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cf);

		container.setConnectionFactory(cf);
		container.setQueueNames(q.getName());
		container.setMessageListener(new MessageListenerAdapter(){
			@Override
			public void onMessage(org.springframework.amqp.core.Message message, Channel channel) throws Exception {
				log.info(message);

				MessageProperties props = message.getMessageProperties();
				if(props.getReceivedExchange().equals(ROOM_FANOUT_EXCHANGE)){
					// TODO Sent to all rooms
				}else {
					String room = "/".concat(props.getReceivedRoutingKey());
					webSocket.convertAndSend(room, new String(message.getBody()));
				}
			}
		});

		if (!container.isRunning()) {
			container.start();
		}	
	}

	private String getRoomId(Room room){
		return "room."+room.getId();
	}

	private boolean queueExists(String queue){
		return admin.getQueueProperties(queue) != null;
	}
}
