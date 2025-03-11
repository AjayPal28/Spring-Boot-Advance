package com.ajay.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import com.ajay.exception.ResourceNotFoundException;
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
	
	
	 @Test

	    void testCreateNewEmployee_whenAttemptingToCreateEmployeeWithExistingEmail_thenThrowException() {
//	        arrange
	        when(employeeRepository.findByEmail(mockEmployeeDto.getEmail())).thenReturn(List.of(mockEmployee));
//	        act and assert

	        assertThatThrownBy(() -> employeeServiceImpl.createNewEmployee(mockEmployeeDto))
	                .isInstanceOf(RuntimeException.class)
	                .hasMessage("Employee already exists with email: "+mockEmployee.getEmail());

	        verify(employeeRepository).findByEmail(mockEmployeeDto.getEmail());
	        verify(employeeRepository, never()).save(any());
	    }
	 
	 
	 @Test
	
	    void testUpdateEmployee_whenAttemptingToUpdateEmail_thenThrowException() {
	        when(employeeRepository.findById(mockEmployeeDto.getId())).thenReturn(Optional.of(mockEmployee));
	        mockEmployeeDto.setName("Random");
	        mockEmployeeDto.setEmail("random@gmail.com");

//	        act and assert

	        assertThatThrownBy(() -> employeeServiceImpl.updateEmployee(mockEmployeeDto.getId(), mockEmployeeDto))
	                .isInstanceOf(RuntimeException.class)
	                .hasMessage("The email of the employee cannot be updated");

	        verify(employeeRepository).findById(mockEmployeeDto.getId());
	        verify(employeeRepository, never()).save(any());
	    }
	 
	 @Test
	    void testUpdateEmployee_whenValidEmployee_thenUpdateEmployee() {
//	        arrange
	        when(employeeRepository.findById(mockEmployeeDto.getId())).thenReturn(Optional.of(mockEmployee));
	        mockEmployeeDto.setName("Random name");
	        mockEmployeeDto.setSalary(199L);


	        Employee newEmployee = modelMapper.map(mockEmployeeDto, Employee.class);
	        when(employeeRepository.save(any(Employee.class))).thenReturn(newEmployee);
//	        act
	        EmployeeDto updatedEmployeeDto = employeeServiceImpl.updateEmployee(mockEmployeeDto.getId(), mockEmployeeDto);

	        assertThat(updatedEmployeeDto).isEqualTo(mockEmployeeDto);

	        verify(employeeRepository).findById(1L);
	        verify(employeeRepository).save(any());
	    }
	 @Test
	    void testDeleteEmployee_whenEmployeeDoesNotExists_thenThrowException() {
	        when(employeeRepository.existsById(1L)).thenReturn(false);

//	        act
	        assertThatThrownBy(() -> employeeServiceImpl.deleteEmployee(1L))
	                .isInstanceOf(ResourceNotFoundException.class)
	                .hasMessage("Employee not found with id: " + 1L);

	        verify(employeeRepository, never()).deleteById(anyLong());
	    }


	    @Test
	    void testDeleteEmployee_whenEmployeeIsValid_thenDeleteEmployee() {
//	        arrange
	        when(employeeRepository.existsById(1L)).thenReturn(true);

	        assertThatCode(() -> employeeServiceImpl.deleteEmployee(1L))
	                .doesNotThrowAnyException();

	        verify(employeeRepository).deleteById(1L);
	    }

}