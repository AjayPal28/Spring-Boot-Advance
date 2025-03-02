package com.ajay.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajay.client.OrdersFeignClient;
import com.ajay.dto.OrderRequestDto;
import com.ajay.dto.ProductDto;
import com.ajay.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

	private final ProductService productService;
	private final OrdersFeignClient ordersFeignClient;
	
	@GetMapping("/fetchOrders")
    public String fetchFromOrdersService(HttpServletRequest httpServletRequest) {
		
        log.info(httpServletRequest.getHeader("x-custom-header"));


        return ordersFeignClient.helloOrders();
	}
	
	@GetMapping
	public ResponseEntity<List<ProductDto>> getAllnventory() {
		List<ProductDto> inventories = productService.getAlLInventory();
		return ResponseEntity.ok(inventories);

	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductDto> getInventorylyId(@PathVariable Long id) {
		ProductDto inventory = productService.getProducteyld(id);
		return ResponseEntity.ok(inventory);
	}
	@PutMapping("reduce-stocks")
    public ResponseEntity<Double> reduceStocks(@RequestBody OrderRequestDto orderRequestDto) {
        Double totalPrice = productService.reduceStocks(orderRequestDto);
        return ResponseEntity.ok(totalPrice);
    }
}
