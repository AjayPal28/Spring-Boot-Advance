package com.ajay.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ajay.entities.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
