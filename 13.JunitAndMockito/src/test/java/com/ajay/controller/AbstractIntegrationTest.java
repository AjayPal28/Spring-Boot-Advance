package com.ajay.controller;

import com.ajay.TestContainerConfiguration;
import com.ajay.dto.EmployeeDto;
import com.ajay.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient(timeout = "100000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfiguration.class)
public class AbstractIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    Employee testEmployee = Employee.builder()
//            .id(1L)
                .email("anuj@gmail.com")
                .name("Anuj")
                .salary(200L)
                .build();
    EmployeeDto testEmployeeDto = EmployeeDto.builder()
//            .id(1L)
                .email("anuj@gmail.com")
                .name("Anuj")
                .salary(200L)
                .build();
}
