package com.ajay.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/posts").permitAll()
						.requestMatchers("/posts/**").hasAnyRole("ADMIN")
						.anyRequest().authenticated())
				.formLogin(Customizer.withDefaults());

		return httpSecurity.build();

	}

	@Bean
	UserDetailsService myInMermoryUserDeatilsServices() {
		UserDetails user1 = User.withUsername("ajay").password(bCryptPasswordEncoder.encode("ajay")).roles("USER")
				.build();
		UserDetails user2 = User.withUsername("sonu").password(bCryptPasswordEncoder.encode("sonu")).roles("ADMIN")
				.build();
		
		return new InMemoryUserDetailsManager(user1, user2);
	}

}
