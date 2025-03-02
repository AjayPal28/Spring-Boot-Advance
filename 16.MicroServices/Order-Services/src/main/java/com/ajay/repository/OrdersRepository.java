package com.ajay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ajay.enity.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long>{

}
