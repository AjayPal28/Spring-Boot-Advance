package com.ajay.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "Order-Services", path = "/orders")
public interface OrdersFeignClient {

	@GetMapping("/core/helloOrders")
	String helloOrders();
	
}
