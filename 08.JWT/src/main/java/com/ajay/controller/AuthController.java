package com.ajay.controller;

import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajay.dto.LoginDto;
import com.ajay.dto.LoginResponseDto;
import com.ajay.dto.SignUpDto;
import com.ajay.dto.UserDto;
import com.ajay.services.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<UserDto> postMethodName(@RequestBody SignUpDto signUpDto) {

		UserDto userDto = authService.signUp(signUpDto);

		return ResponseEntity.ok(userDto);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> postMethodName(@RequestBody LoginDto loginDto,
			HttpServletRequest httpRequest, HttpServletResponse httpResponse) {

		LoginResponseDto loginResponseDto = authService.login(loginDto);

		Cookie cookie = new Cookie("refreshToken", loginResponseDto.getRefreshToken());
		cookie.setHttpOnly(true);
		httpResponse.addCookie(cookie);

		return ResponseEntity.ok(loginResponseDto);
	}

	@PostMapping("/getAccessTokenFromRefreshToken")
	public ResponseEntity<LoginResponseDto> getAccessTokenFromRefreshToken(HttpServletRequest httpServletRequest) {
		String refreshToken = Arrays.stream(httpServletRequest.getCookies())
				.filter(cookies -> "refreshToken".equals(cookies.getName())).findFirst().map(Cookie::getValue)
				.orElseThrow(() -> new AuthenticationServiceException("Refresh Token Not Found In Cookies"));

		LoginResponseDto loginResponseDto = authService.getAccessTokenFromRefreshToken(refreshToken);

		return ResponseEntity.ok(loginResponseDto);
	}

}
