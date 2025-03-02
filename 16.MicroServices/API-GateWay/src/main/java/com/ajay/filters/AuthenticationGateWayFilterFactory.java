package com.ajay.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

import com.ajay.services.JwtService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthenticationGateWayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGateWayFilterFactory.Config>{

	private final JwtService jwtService;
	
	@Data
	public static class Config{
		private boolean enabled;
	}

	
	
	public AuthenticationGateWayFilterFactory(JwtService jwtService) {
		super(Config.class);
		this.jwtService=jwtService;
	}


	@Override
	public GatewayFilter apply(Config config) {
		return (exchange,chain)->{
			
			String authorizationHeader=exchange.getRequest().getHeaders().getFirst("Authorization");
			
			if(authorizationHeader==null) {
				exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
				return exchange.getResponse().setComplete();
			}
			
			String token=authorizationHeader.split("Bearer ")[1];
			Long userId=jwtService.getUserIdFromToken(token);
			
			exchange.getRequest().mutate().header("X-User-Id", userId.toString()).build();
			
			return chain.filter(exchange);
			
		};
		//return null;
	}
	
	
	
}
