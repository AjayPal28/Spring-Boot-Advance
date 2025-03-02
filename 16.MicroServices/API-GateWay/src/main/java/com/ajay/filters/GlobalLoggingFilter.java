package com.ajay.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalLoggingFilter implements GlobalFilter, Ordered{

	@Override
	public int getOrder() {
		return 5;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("Logging From Global FIlter :{}",exchange.getRequest().getURI());
		return chain.filter(exchange).then(Mono.fromRunnable(()->{
			log.info("logging from global filter Post:{}",exchange.getResponse().getStatusCode());
		}));
	}

}
