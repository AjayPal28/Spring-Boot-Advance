package com.ajay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class EmployeeService {

	@Autowired
	private RestClient restClient;

	public List<String> getEmployeeName() {
		try {
			List<String> a = restClient.get()
							.uri("test")
							.retrieve()
							.body(new ParameterizedTypeReference<>() {
								});
			return a;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
