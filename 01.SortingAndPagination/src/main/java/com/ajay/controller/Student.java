package com.ajay.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajay.entity.StudentEntity;
import com.ajay.services.StudentServices;

import java.util.List;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/Student")
public class Student {

	private final StudentServices studentServices;

	private final Integer PAGE_SIZE=5;
	
	public Student(StudentServices studentServices) {
		super();
		this.studentServices = studentServices;
	}

	@GetMapping(path = "allStudent")
	public List<StudentEntity> getAllStudent(@RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "1") Integer pageNumber) {
		
		Pageable pageable=PageRequest.of(pageNumber, PAGE_SIZE, Sort.by(sortBy));
		
		return  studentServices.getAllStudent(pageable);
	}
	
	
}
