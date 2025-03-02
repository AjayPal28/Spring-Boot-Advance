package com.ajay.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class OrderRequestDto {

	private Long id;
	private List<OrderRequestItemDto>items;
	private BigDecimal totalPrice;
}
