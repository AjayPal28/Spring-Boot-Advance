package com.ajay.controller;

import org.springframework.web.bind.annotation.RestController;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class StudentController {

	Logger logger=LoggerFactory.getLogger(StudentController.class);
	
	@GetMapping(path= "/AddStudent")
	public String addStudent(@RequestParam String name) {
		logger.info("name : "+name);
		return name;
	}
	
	
}
