package com.ajay.user_service.controler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajay.user_service.dto.CreateUserRequestDto;
import com.ajay.user_service.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	@Value("${kafka.topic.user-random-topic}")
	private String KAFKA_RANDOM_USER_TOPIC;
	
	private final KafkaTemplate<String, String> kafkaTemplate;

	
	 private final UserService userService;

	    @PostMapping
	    public ResponseEntity<String> createUser(@RequestBody CreateUserRequestDto createUserRequestDto) {
	        userService.createUser(createUserRequestDto);
	        return ResponseEntity.ok("User is created");
	    }
	
	
	@PostMapping("/{message}")
	public ResponseEntity<String> sendMessage(@PathVariable String message) {

		kafkaTemplate.send(KAFKA_RANDOM_USER_TOPIC, message);

		return ResponseEntity.ok("Message queued");
	}

}
