package com.ajay.services;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import com.ajay.entities.Session;
import com.ajay.entities.User;
import com.ajay.repositories.SessionRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionService {

	@Value("${session.limt}")
	private int SESSION_LIMIT;
	private final SessionRepository sessionRepository;

	public void generateNewSession(User user, String refreshToken) {
		List<Session> userSession = sessionRepository.findByUser(user);

		if (userSession.size() == SESSION_LIMIT) {
			userSession.sort(Comparator.comparing(Session::getLastUsedAt));
			sessionRepository.delete(userSession.getFirst());
		}

		sessionRepository.save(Session.builder().refreshToken(refreshToken).user(user).build());
	}

	public void validateSession(String refreshToken) {

		Session session = sessionRepository.findByRefreshToken(refreshToken)
				.orElseThrow(() -> new SessionAuthenticationException("Session not found"));

		session.setLastUsedAt(LocalDateTime.now());
		sessionRepository.save(session);
	}
	
	public void logout(String refreshToken) {

		Session session = sessionRepository.deleteByRefreshToken(refreshToken)
				.orElseThrow(() -> new SessionAuthenticationException("Session not found"));

	}

}
