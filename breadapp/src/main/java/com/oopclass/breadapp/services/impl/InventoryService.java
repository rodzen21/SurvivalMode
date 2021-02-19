package com.oopclass.breadapp.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopclass.breadapp.models.Inventory;
import com.oopclass.breadapp.repository.InventoryRepository;
import com.oopclass.breadapp.services.IInventoryService;

/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Service
public class InventoryService implements IInventoryService {
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Override
	public Inventory save(Inventory entity) {
		return inventoryRepository.save(entity);
	}

	@Override
	public Inventory update(Inventory entity) {
		return inventoryRepository.save(entity);
	}

	@Override
	public void delete(Inventory entity) {
		inventoryRepository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		inventoryRepository.deleteById(id);
	}

	@Override
	public Inventory find(Long id) {
		return inventoryRepository.findById(id).orElse(null);
	}

	@Override
	public List<Inventory> findAll() {
		return inventoryRepository.findAll();
	}

	@Override
	public void deleteInBatch(List<Inventory> inventorys) {
		inventoryRepository.deleteInBatch(inventorys);
	}
	
}
