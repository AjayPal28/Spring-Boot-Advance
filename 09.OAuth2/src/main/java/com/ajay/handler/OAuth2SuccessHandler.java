package com.ajay.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ajay.entities.User;
import com.ajay.services.JwtService;
import com.ajay.services.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final UserService userService;
	private final JwtService jwtService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		
		OAuth2AuthenticationToken auth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;
		
		DefaultOAuth2User defaultOAuth2User=(DefaultOAuth2User) auth2AuthenticationToken.getPrincipal();
		
		log.info("email : "+ defaultOAuth2User.getAttribute("email"));
		
		String emailId=defaultOAuth2User.getAttribute("email");
		
		User user=userService.getUserByEmail(emailId);
		if(user==null) {
			User newUser=User.builder().email(emailId).name(defaultOAuth2User.getAttribute("name")).build();
			userService.save(newUser);
		}
		
		String accessToken=jwtService.generateAccessToken(user);
		String refreshToken=jwtService.generateRefreshToken(user);
		
		Cookie cookie = new Cookie("refreshToken", refreshToken);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
		
		String frontEndUrl = "http://localhost:8080/home.html?token="+accessToken;
		
		getRedirectStrategy().sendRedirect(request, response, frontEndUrl);
	}

	
	
}
