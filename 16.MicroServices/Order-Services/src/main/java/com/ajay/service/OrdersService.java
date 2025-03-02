package com.ajay.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ajay.client.InventoryOpenFeignClient;
import com.ajay.dto.OrderRequestDto;
import com.ajay.enity.OrderItem;
import com.ajay.enity.OrderStatus;
import com.ajay.enity.Orders;
import com.ajay.repository.OrdersRepository;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersService {

	private final OrdersRepository orderRepository;
	private final ModelMapper modelMapper;
	private final InventoryOpenFeignClient inventoryOpenFeignClient;

	public List<OrderRequestDto> getAllOrders() {

		log.info("Fetching all orders");

		List<Orders> orders = orderRepository.findAll();

		return orders.stream().map(order -> modelMapper.map(order, OrderRequestDto.class)).toList();
	}

	public OrderRequestDto getOrdertyld(Long id) {
		
		log.info("Fetehing orden with ID: {}", id);
		
		Orders order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
		
		return modelMapper.map(order, OrderRequestDto.class);

	}
	@Retry(name = "inventoryRetry", fallbackMethod = "createOrderFallback")
	@RateLimiter(name = "inventoryRefreshLimiter", fallbackMethod = "createOrderFallback")
	public OrderRequestDto createOrder(OrderRequestDto orderRequestDto) {
        log.info("Calling the createOrder method");
        Double totalPrice = inventoryOpenFeignClient.reduceStocks(orderRequestDto);

        Orders orders = modelMapper.map(orderRequestDto, Orders.class);
        for(OrderItem orderItem: orders.getItems()) {
            orderItem.setOrder(orders);
        }
        orders.setTotalPrice(totalPrice);
        orders.setOrderStatus(OrderStatus.CONFIRMED);

        Orders savedOrder = orderRepository.save(orders);

        return modelMapper.map(savedOrder, OrderRequestDto.class);
    }
	
	public OrderRequestDto createOrderFallback(OrderRequestDto orderRequestDto, Throwable throwable) {
		log.error("Fallback occured due to : {}", throwable.getMessage());
		return new OrderRequestDto();
	}
}
