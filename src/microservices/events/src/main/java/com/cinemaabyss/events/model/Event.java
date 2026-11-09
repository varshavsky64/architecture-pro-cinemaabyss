package com.cinemaabyss.events.model;

import java.time.Instant;

/**
 * Event envelope: written to Kafka and returned to the client.
 *
 * @param payload request body of the event, its fields depend on the event type
 */
public record Event(String id, EventType type, Instant timestamp, Object payload) {
}
