package com.oopclass.breadapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oopclass.breadapp.models.Receipt;

/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

	//User findByEmail(String email);
}
