package com.ajay.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ajay.entities.Session;
import com.ajay.entities.User;

@Repository
public interface SessionRepository  extends JpaRepository<Session, Long>{

	List<Session> findByUser(User user);

	Optional<Session> findByRefreshToken(String refreshToken);

	Optional<Session> deleteByRefreshToken(String refreshToken);

}
