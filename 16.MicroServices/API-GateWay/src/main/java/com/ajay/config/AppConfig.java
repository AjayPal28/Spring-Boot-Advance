package com.ajay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Capability;
import feign.micrometer.MicrometerCapability;
import io.micrometer.core.instrument.MeterRegistry;

@Configuration
public class AppConfig {

	@Bean
	public Capability capability(final MeterRegistry registry) {
	    return new MicrometerCapability(registry);
	}
}
