package com.cinemaabyss.proxy.predicate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * Strangler Fig feature flag as a route predicate, used in application.yml as {@code MigrationPercent}.
 * It matches the given percent of requests, chosen at random; requests that do not match
 * fall through to the next route. With gradual migration off the migration is complete,
 * so every request matches.
 */
@Component
public class MigrationPercentRoutePredicateFactory
        extends AbstractRoutePredicateFactory<MigrationPercentRoutePredicateFactory.Config> {

    public MigrationPercentRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return exchange -> !config.gradualMigration()
                || ThreadLocalRandom.current().nextInt(100) < config.percent();
    }

    public record Config(boolean gradualMigration, @Min(0) @Max(100) int percent) {
    }
}
