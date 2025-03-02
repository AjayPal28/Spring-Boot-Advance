package com.ajay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ajay.entities.enums.Permissions;
import com.ajay.entities.enums.Role;
import com.ajay.filter.JwtAuthFilter;
import com.ajay.handler.OAuth2SuccessHandler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

	private final JwtAuthFilter jwtAuthFilter;
	private final OAuth2SuccessHandler auth2SuccessHandler;
	private final String [] publicRotes= {
			 "/error", "/auth/**","/home.html"
	};
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.authorizeHttpRequests(auth -> auth.requestMatchers(publicRotes).permitAll()
				.anyRequest().authenticated())
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.oauth2Login(oauth2Config->oauth2Config.failureUrl("/login?error=true")
						.successHandler(auth2SuccessHandler));
				
				

		return httpSecurity.build();

	}

}
