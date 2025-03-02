package com.ajay.testing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.offset;

import org.assertj.core.data.Offset;
import org.hamcrest.core.IsInstanceOf;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestingApp {

	@BeforeEach
	public void beforeEach() {
		log.info("starting the method, setting up config");
	}

	@AfterEach
	public void afterEach() {
		log.info("tearing down the method ");
	}

	@BeforeAll
	public static void beforeAll() {
		log.info("beforeAll");
	}

	@AfterAll
	public static void afterAll() {
		log.info("afterAll");
	}

	@Test
	public void name() {
		log.info("hello");
	}

	@Test
	public void name1() {
		// Assertions.assertEquals(8, addTwoNumber(5, 3));

		int result = addTwoNumber(3, 5);
		assertThat(result).isEqualTo(8).isCloseTo(9, Offset.offset(1));
		
		assertThat("ajay").isEqualTo("ajay").startsWith("aj").endsWith("ay").hasSize(4);
		
		//divideTwoNumber(1, 0);
		
		assertThatThrownBy(()-> divideTwoNumber(1, 0)).isInstanceOf(ArithmeticException.class).hasMessage("Tried to divide by zero");
	}

	public int addTwoNumber(int a, int b) {
		return a + b;
	}
	
	public double divideTwoNumber(int a, int b) {
		try {
			return  a/b;
		} catch (ArithmeticException e) {
			log.error("Not divide By Zero"+e.getLocalizedMessage());
			throw new ArithmeticException();
			
		}
	}

}
