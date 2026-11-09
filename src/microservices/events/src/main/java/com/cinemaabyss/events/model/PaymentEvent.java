package com.cinemaabyss.events.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

public record PaymentEvent(
        @NotNull Long paymentId,
        @NotNull Long userId,
        @NotNull BigDecimal amount,
        @NotBlank String status,
        @NotNull Instant timestamp,
        String methodType) {
}
