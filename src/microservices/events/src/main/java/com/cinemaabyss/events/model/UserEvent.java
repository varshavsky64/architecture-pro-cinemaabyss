package com.cinemaabyss.events.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

public record UserEvent(
        @NotNull Long userId,
        String username,
        String email,
        @NotBlank String action,
        @NotNull Instant timestamp) {
}
