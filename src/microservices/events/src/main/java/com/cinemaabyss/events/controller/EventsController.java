package com.cinemaabyss.events.controller;

import com.cinemaabyss.events.model.EventResponse;
import com.cinemaabyss.events.model.EventType;
import com.cinemaabyss.events.model.MovieEvent;
import com.cinemaabyss.events.model.PaymentEvent;
import com.cinemaabyss.events.model.UserEvent;
import com.cinemaabyss.events.producer.EventProducer;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventsController {

    private final EventProducer producer;

    public EventsController(EventProducer producer) {
        this.producer = producer;
    }

    @GetMapping("/health")
    public Map<String, Boolean> health() {
        return Map.of("status", true);
    }

    @PostMapping("/movie")
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponse movie(@Valid @RequestBody MovieEvent event) {
        return producer.send(EventType.MOVIE, event.movieId(), event.action(), event);
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponse user(@Valid @RequestBody UserEvent event) {
        return producer.send(EventType.USER, event.userId(), event.action(), event);
    }

    @PostMapping("/payment")
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponse payment(@Valid @RequestBody PaymentEvent event) {
        return producer.send(EventType.PAYMENT, event.paymentId(), event.status(), event);
    }
}
