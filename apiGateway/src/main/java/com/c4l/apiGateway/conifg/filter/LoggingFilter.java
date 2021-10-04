package com.c4l.apiGateway.conifg.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter implements GlobalFilter {

	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, 
			GatewayFilterChain chain) {
		log.info("Path of the request received -> {}", 
				exchange.getRequest().getPath(),exchange.getRequest());
		
		log.info("Response Status is {} for request {}", 
				exchange.getResponse().getStatusCode(),exchange.getRequest().getPath());
		
		
		return chain.filter(exchange);
	}

}