package com.ajay.services;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ajay.dto.LoginDto;
import com.ajay.dto.LoginResponseDto;
import com.ajay.dto.SignUpDto;
import com.ajay.dto.UserDto;
import com.ajay.entities.User;
import com.ajay.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {

	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserService userService;
	private final SessionService sessionService;

	public UserDto signUp(SignUpDto signUpDto) {
		Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());
		if (user.isPresent()) {
			throw new BadCredentialsException("User with email already exits " + signUpDto.getEmail());
		}

		User toBeCreatedUser = modelMapper.map(signUpDto, User.class);
		toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword()));

		User savedUser = userRepository.save(toBeCreatedUser);
		return modelMapper.map(savedUser, UserDto.class);
	}

	public User save(User newUser) {
		return userRepository.save(newUser);
	}

	public LoginResponseDto login(LoginDto loginDto) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

		User user = (User) authentication.getPrincipal();
		String accessToken = jwtService.generateAccessToken(user);
		String refreshToken = jwtService.generateRefreshToken(user);

		sessionService.generateNewSession(user, refreshToken);
		
		return new LoginResponseDto(user.getId(), accessToken, refreshToken);

	}

	public LoginResponseDto getAccessTokenFromRefreshToken(String refreshToken) {
		Long id = jwtService.getUserIdFromToken(refreshToken);
		sessionService.validateSession(refreshToken);
		
		User user = userService.getUserById(id);
		String accesToken = jwtService.generateAccessToken(user);
				
		return new LoginResponseDto(id, accesToken, refreshToken);
	}
	
	public LoginResponseDto logout(String refreshToken) {
		Long id = jwtService.getUserIdFromToken(refreshToken);
		sessionService.logout(refreshToken);
			
		return new LoginResponseDto(id, null, null);
	}
}
