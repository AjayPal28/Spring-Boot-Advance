package com.ajay.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@Slf4j
@SpringBootTest
public class ShipmentServiceImplTest {

	 	@Autowired
	    private ShipmentServiceImpl shipmentService;

	    @Test
	    void aopTestOrderPackage() {
	        String orderString = shipmentService.orderPackage(0L);
	        log.info(orderString);
	    }

	    @Test
	    void aopTestTrackPackage() {
	        shipmentService.trackPackage(4L);
	    }
}
