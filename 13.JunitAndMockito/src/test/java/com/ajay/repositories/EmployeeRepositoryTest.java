package com.ajay.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.ajay.TestContainerConfiguration;
import com.ajay.entities.Employee;

//@SpringBootTest
@DataJpaTest
@Import(TestContainerConfiguration.class)
//@AutoConfigureTestDatabase(replace = Replace.ANY)
public class EmployeeRepositoryTest {

	@Autowired
	private EmployeeRepository employeeRepository;

	private Employee employee;

	@BeforeEach
	void setUp() {
		employee = employee.builder().email("AjayPal@gmail.com").name("Ajay").salary(100000l).build();
	}

	@Test
	void testFindByEmail_whenEmailIsPresent_thenReturnEmployee() {

		// arrange
		employeeRepository.save(employee);

		// act
		List<Employee> employeeList = employeeRepository.findByEmail(employee.getEmail());

		// assert
		assertThat(employeeList).isNotEmpty();
		assertThat(employeeList).isNotNull();
		assertThat(employeeList.get(0).getEmail()).isEqualTo(employee.getEmail());
	}

	@Test
	void testFindByEmail_whenEmailIsNot_Found_thenReturnEmptyEmployeeList() {
		// arrange
		String email = "failed@gmail.com";

		// act
		List<Employee> employeeList = employeeRepository.findByEmail(email);

		// assert
		assertThat(employeeList).isNullOrEmpty();;
//		assertThat(employeeList).isNotNull();
//		assertThat(employeeList.get(0).getEmail()).isEqualTo(employee.getEmail());
	}
}
