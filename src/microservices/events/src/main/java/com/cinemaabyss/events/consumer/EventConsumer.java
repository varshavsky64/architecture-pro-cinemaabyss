package com.cinemaabyss.events.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Reads the events back from Kafka and processes them by writing them to the service log.
 */
@Component
public class EventConsumer {

    private static final Logger log = LoggerFactory.getLogger(EventConsumer.class);

    @KafkaListener(topics = {"${events.topics.movie}", "${events.topics.user}", "${events.topics.payment}"})
    public void consume(ConsumerRecord<String, String> message) {
        log.info("Consumed event from topic {}, partition {}, offset {}: {}",
                message.topic(), message.partition(), message.offset(), message.value());
    }
}
