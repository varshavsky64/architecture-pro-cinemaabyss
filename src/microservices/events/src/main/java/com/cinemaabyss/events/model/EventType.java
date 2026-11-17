package com.cinemaabyss.events.model;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Locale;

public enum EventType {
    MOVIE, USER, PAYMENT;

    /**
     * Name of the type in the API and in event ids: movie, user, payment.
     */
    @JsonValue
    public String value() {
        return name().toLowerCase(Locale.ROOT);
    }
}
