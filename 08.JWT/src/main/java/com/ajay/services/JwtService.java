package com.ajay.services;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ajay.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${jwt.secretkey}")
	private String jwtSecretKey;

	public SecretKey getSecretKet() {
		return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
	}

	public String generateAccessToken(User user) {
		return Jwts.builder().subject(user.getId().toString()).claim("email", user.getEmail())
				.claim("roles", Set.of("ADMIN", "USER")).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 60 * 10 * 1000)).signWith(getSecretKet()).compact();
	}
	
	public String generateRefreshToken(User user) {
		return Jwts.builder().subject(user.getId().toString()).claim("email", user.getEmail())
				
				.expiration(new Date(System.currentTimeMillis() + 60 * 60 * 60 * 1000)).signWith(getSecretKet()).compact();
	}

	public long getUserIdFromToken(String token) {
		Claims claims = Jwts.parser().verifyWith(getSecretKet()).build().parseSignedClaims(token).getPayload();
		return Long.valueOf(claims.getSubject());
	}
}
