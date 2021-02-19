package com.oopclass.breadapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oopclass.breadapp.models.Order;

/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	//User findByEmail(String email);
}
