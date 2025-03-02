package com.ajay.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ajay.entities.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByEmail(String email);
}
