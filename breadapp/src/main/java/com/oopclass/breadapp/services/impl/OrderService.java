package com.oopclass.breadapp.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopclass.breadapp.models.Order;
import com.oopclass.breadapp.repository.OrderRepository;
import com.oopclass.breadapp.services.IOrderService;

/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Service
public class OrderService implements IOrderService {
	
	@Autowired
	private OrderRepository OrderRepository;
	
	@Override
	public Order save(Order entity) {
		return OrderRepository.save(entity);
	}

	@Override
	public Order update(Order entity) {
		return OrderRepository.save(entity);
	}

	@Override
	public void delete(Order entity) {
		OrderRepository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		OrderRepository.deleteById(id);
	}

	@Override
	public Order find(Long id) {
		return OrderRepository.findById(id).orElse(null);
	}

	@Override
	public List<Order> findAll() {
		return OrderRepository.findAll();
	}

	@Override
	public void deleteInBatch(List<Order> orders) {
		OrderRepository.deleteInBatch(orders);
	}
	
}
