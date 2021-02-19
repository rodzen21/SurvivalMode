package com.oopclass.breadapp.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopclass.breadapp.models.Receipt;
import com.oopclass.breadapp.repository.ReceiptRepository;
import com.oopclass.breadapp.services.IReceiptService;

/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Service
public class ReceiptService implements IReceiptService {
	
	@Autowired
	private ReceiptRepository receiptRepository;
	
	@Override
	public Receipt save(Receipt entity) {
		return receiptRepository.save(entity);
	}

	@Override
	public Receipt update(Receipt entity) {
		return receiptRepository.save(entity);
	}

	@Override
	public void delete(Receipt entity) {
		receiptRepository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		receiptRepository.deleteById(id);
	}

	@Override
	public Receipt find(Long id) {
		return receiptRepository.findById(id).orElse(null);
	}

	@Override
	public List<Receipt> findAll() {
		return receiptRepository.findAll();
	}

	@Override
	public void deleteInBatch(List<Receipt> receipts) {
		receiptRepository.deleteInBatch(receipts);
	}
	
}
