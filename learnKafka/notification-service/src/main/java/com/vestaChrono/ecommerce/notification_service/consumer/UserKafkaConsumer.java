package com.vestaChrono.ecommerce.notification_service.consumer;

import com.vestaChrono.ecommerce.event.UserCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserKafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(UserKafkaConsumer.class);

    @KafkaListener(topics = "user-created-topic")
    public void handleUserCreated(UserCreatedEvent userCreatedEvent) {
        log.info("handleUserCreated: {}", userCreatedEvent);
    }

    @KafkaListener(topics = "user-random-topic")
    public void handleUserRandomTopic1(String message) {
        log.info("handleUserRandomTopic1: {}", message);
    }

    @KafkaListener(topics = "user-random-topic")
    public void handleUserRandomTopic2(String message) {
        log.info("handleUserRandomTopic2: {}", message);
    }

    @KafkaListener(topics = "user-random-topic")
    public void handleUserRandomTopic3(String message) {
        log.info("handleUserRandomTopic3: {}", message);
    }

}
