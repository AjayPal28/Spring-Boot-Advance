package com.ajay.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.Import;

import com.ajay.TestContainerConfiguration;
import com.ajay.dto.EmployeeDto;
import com.ajay.entities.Employee;
import com.ajay.repositories.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Import(TestContainerConfiguration.class)
public class EmployeeServiceImplTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@Spy
	private ModelMapper modelMapper;

	@InjectMocks
	private EmployeeServiceImpl employeeServiceImpl;

	private Employee mockEmployee;

	private EmployeeDto mockEmployeeDto;

	@BeforeEach
	void setUp() {
		mockEmployee = Employee.builder().id(1l).email("Ajaypal@gmail.com").name("ajay").salary(100000l).build();
		mockEmployeeDto = modelMapper.map(mockEmployee, EmployeeDto.class);
	}

	@Test
	void testGetEmployeeById_WhenEmployeeIdIsPresent_thenReturnEmployeeDTO() {
		// assign
		Long id = mockEmployee.getId();

		when(employeeRepository.findById(id)).thenReturn(Optional.of(mockEmployee));

		// act
		EmployeeDto employeeDto = employeeServiceImpl.getEmployeeById(id);

		// assert

		assertThat(employeeDto.getId()).isEqualTo(id);
		assertThat(employeeDto.getEmail()).isEqualTo(mockEmployee.getEmail());
		verify(employeeRepository, atLeast(1)).findById(id);

	}

	@Test
	void testCreateNewEmployee_WhenValidEmployee_ThenCreateNewEmployee() {

		// assign
		when(employeeRepository.findByEmail(anyString())).thenReturn(List.of());
		when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee);
		
		//act
		
		EmployeeDto employeeDto=employeeServiceImpl.createNewEmployee(mockEmployeeDto);
		
		//assert
		assertThat(employeeDto).isNotNull();
		assertThat(employeeDto.getEmail()).isEqualTo(mockEmployeeDto.getEmail());
		verify(employeeRepository).save(any(Employee.class));
		
		
		 ArgumentCaptor<Employee>employeeArgumentCaptor=ArgumentCaptor.forClass(Employee.class);
		
		 verify(employeeRepository).save(employeeArgumentCaptor.capture());
		 
		 Employee captureEmployee=employeeArgumentCaptor.getValue();
		 
		 assertThat(captureEmployee.getEmail()).isEqualTo(mockEmployee.getEmail());
	}

}