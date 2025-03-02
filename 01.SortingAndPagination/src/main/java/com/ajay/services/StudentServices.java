package com.ajay.services;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ajay.entity.StudentEntity;
import com.ajay.repositories.StudentRepo;

@Service
public class StudentServices {

	private final StudentRepo studentRepo;
	
	public StudentServices(StudentRepo studentRepo) {
		super();
		this.studentRepo = studentRepo;
	}

	public List<StudentEntity> getAllStudent(Pageable pageable) {
		return (List<StudentEntity>) studentRepo.findAll(pageable).getContent();
	}

}
