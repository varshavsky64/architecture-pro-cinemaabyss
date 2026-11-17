package com.cinemaabyss.events.model;

/**
 * Where Kafka stored the event: partition and offset.
 */
public record EventResponse(String status, int partition, long offset, Event event) {
}
