package com.ajay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ajay.entity.Product;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Long>{

}
