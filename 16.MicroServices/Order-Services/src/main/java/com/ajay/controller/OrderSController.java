package com.ajay.controller;

import java.util.List;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajay.dto.OrderRequestDto;
import com.ajay.service.OrdersService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
@RefreshScope
public class OrderSController {

	private final OrdersService ordersService;

	@GetMapping("/helloOrders")
	public String helloOrders() {
		return "Hello from Orders Service";
	}

	@PostMapping("/create-order")
	public ResponseEntity<OrderRequestDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
		OrderRequestDto orderRequestDto1 = ordersService.createOrder(orderRequestDto);
		return ResponseEntity.ok(orderRequestDto1);
	}

	@GetMapping
	public ResponseEntity<List<OrderRequestDto>> getAllorders(HttpServletRequest httpServletRequest) {
		log.info("Fetching all orders via controller");
		List<OrderRequestDto> orders = ordersService.getAllOrders();
		return ResponseEntity.ok(orders);
	}

	@GetMapping("/{id}")
	public ResponseEntity<OrderRequestDto> getOrdersyld(@PathVariable(name = "id") Long id) {
		log.info("Fetching Order with ID :{} via Controller", id);
		OrderRequestDto order = ordersService.getOrdertyld(id);
		return ResponseEntity.ok(order);

	}

}
