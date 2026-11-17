package com.cinemaabyss.events.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record MovieEvent(
        @NotNull Long movieId,
        @NotBlank String title,
        @NotBlank String action,
        Long userId,
        Double rating,
        List<String> genres,
        String description) {
}
