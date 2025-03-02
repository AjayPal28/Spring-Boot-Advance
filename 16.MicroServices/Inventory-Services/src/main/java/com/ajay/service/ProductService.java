package com.ajay.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ajay.dto.OrderRequestDto;
import com.ajay.dto.OrderRequestItemDto;
import com.ajay.dto.ProductDto;
import com.ajay.entity.Product;
import com.ajay.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ModelMapper modelMapper;

	public List<ProductDto> getAlLInventory() {
		log.info("Fetching all inventory items");
		List<Product> inventories = productRepository.findAll();

		return inventories.stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();
	}

	public ProductDto getProducteyld(Long id) {
		log.info("Fetching Product with ID: {}", id);
		Optional<Product> inventory = productRepository.findById(id);
		return inventory.map(item -> modelMapper.map(item, ProductDto.class))
				.orElseThrow(() -> new RuntimeException("Inventory not found"));

	}
	@Transactional
    public Double reduceStocks(OrderRequestDto orderRequestDto) {
        log.info("Reducing the stocks");
        Double totalPrice = 0.0;
        for(OrderRequestItemDto orderRequestItemDto: orderRequestDto.getItems()) {
            Long productId = orderRequestItemDto.getProductId();
            Integer quantity = orderRequestItemDto.getQuantity();

            Product product = productRepository.findById(productId).orElseThrow(() ->
                    new RuntimeException("Product not found with id: "+productId));

            if(product.getStock() < quantity) {
                throw new RuntimeException("Product cannot be fulfilled for given quantity");
            }

            product.setStock(product.getStock()-quantity);
            productRepository.save(product);
            totalPrice += quantity*product.getPrice();
        }
        return totalPrice;
    }
}
