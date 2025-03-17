package com.ajay.notification_service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ajay.event.UserCreatedEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserKafkaConsumer {

	private static final String KAFKA_RANDOM_USER_TOPIC = "user-random-topic";
	private static final String KAFKA_USER_CREATED_TOPIC = "user-created-topic";

	@KafkaListener(topicPattern = KAFKA_USER_CREATED_TOPIC)
	public void hanldeUserCreatedEvent(UserCreatedEvent userCreatedEvent) {
		log.info("hanldeUserCreatedEvent :{}", userCreatedEvent);
	}

	@KafkaListener(topicPattern = KAFKA_RANDOM_USER_TOPIC)
	public void handleUserRandomTopic(String message) {
		log.info("Message received :" + message);
	}

}
