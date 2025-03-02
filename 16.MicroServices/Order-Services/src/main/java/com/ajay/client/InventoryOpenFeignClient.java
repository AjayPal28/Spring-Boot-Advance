package com.ajay.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ajay.dto.OrderRequestDto;

@FeignClient(name = "inventory-service", path = "/inventory")
public interface InventoryOpenFeignClient {

	@PutMapping("/products/reduce-stocks")
	Double reduceStocks(@RequestBody OrderRequestDto orderRequestDto);
}
