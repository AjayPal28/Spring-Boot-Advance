package com.example.ajay;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

	@GetMapping
	public String helloWorld() {
		return "Hello World";
	}
	
	
}
