package com.ajay.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajay.service.ShipmentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ShipmentServiceImpl implements ShipmentService {

	@Override
	public String orderPackage(Long orderID) {
		try {
			log.info("Processing the order");
			Thread.sleep(1000);
		} catch (Exception e) {
			log.error("Error Occured While processing the order");
		}
		return "Order Has beeen processed sucessfully, orderId :" + orderID;

	}

	@Override
	@Transactional
	public String trackPackage(Long orderID) {
		try {
			log.info("Tracking the order");
			Thread.sleep(500);
			throw new RuntimeException("Exception occured during track package");
		} catch (InterruptedException e) {
			throw new RuntimeException();
		}

	}

}
