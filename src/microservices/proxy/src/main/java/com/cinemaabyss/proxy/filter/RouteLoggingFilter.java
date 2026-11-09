package com.cinemaabyss.proxy.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Logs where each request is routed, so the traffic split between the monolith and the microservices
 * is visible in the proxy logs.
 */
@Component
public class RouteLoggingFilter implements GlobalFilter {

    private static final Logger log = LoggerFactory.getLogger(RouteLoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var route = exchange.<Route>getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        var request = exchange.getRequest();
        log.info("{} {} -> {} ({})", request.getMethod(), request.getPath(), route.getId(), route.getUri());
        return chain.filter(exchange);
    }
}
