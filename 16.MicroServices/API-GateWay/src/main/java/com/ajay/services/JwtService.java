package com.ajay.services;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

	
	public long getUserIdFromToken(String token) {
		Claims claims = Jwts.parser().verifyWith(getSecretKet()).build().parseSignedClaims(token).getPayload();
		return Long.valueOf(claims.getSubject());
	}
	
	
	public String getUserRoleFromToken(String token) {
		Claims claims = Jwts.parser().verifyWith(getSecretKet()).build().parseSignedClaims(token).getPayload();
		return claims.get("role",String.class);
	}
}
