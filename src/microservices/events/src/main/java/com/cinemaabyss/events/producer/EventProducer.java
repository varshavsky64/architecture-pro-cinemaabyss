package com.cinemaabyss.events.producer;

import com.cinemaabyss.events.config.EventsProperties;
import com.cinemaabyss.events.model.Event;
import com.cinemaabyss.events.model.EventResponse;
import com.cinemaabyss.events.model.EventType;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

/**
 * Wraps a request into an event and writes it to the Kafka topic of its type.
 */
@Component
public class EventProducer {

    private static final Logger log = LoggerFactory.getLogger(EventProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final JsonMapper jsonMapper;
    private final EventsProperties properties;

    public EventProducer(KafkaTemplate<String, String> kafkaTemplate, JsonMapper jsonMapper,
                         EventsProperties properties) {
        this.kafkaTemplate = kafkaTemplate;
        this.jsonMapper = jsonMapper;
        this.properties = properties;
    }

    /**
     * Sends the event and waits for Kafka to confirm it: the response contains the partition and offset.
     *
     * @param entityId id of the movie, user or payment; it is the message key, so events
     *                 of one entity stay in one partition and keep their order
     */
    public EventResponse send(EventType type, long entityId, String action, Object payload) {
        var event = new Event(type.value() + "-" + entityId + "-" + action, type, Instant.now(), payload);
        var topic = properties.topics().get(type);
        var result = kafkaTemplate.send(topic, String.valueOf(entityId), jsonMapper.writeValueAsString(event)).join();
        var metadata = result.getRecordMetadata();
        log.info("Produced event {} to topic {}, partition {}, offset {}",
                event.id(), topic, metadata.partition(), metadata.offset());
        return new EventResponse("success", metadata.partition(), metadata.offset(), event);
    }
}
