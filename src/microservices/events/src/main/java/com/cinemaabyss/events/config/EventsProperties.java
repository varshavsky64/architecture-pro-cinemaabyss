package com.cinemaabyss.events.config;

import com.cinemaabyss.events.model.EventType;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Kafka topic for each event type, see application.yml.
 */
@ConfigurationProperties(prefix = "events")
public record EventsProperties(Map<EventType, String> topics) {
}
