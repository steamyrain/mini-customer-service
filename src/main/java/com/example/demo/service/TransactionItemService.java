package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.TransactionItem;
import com.example.demo.repository.TransactionItemRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionItemService {
	private final TransactionItemRepository transactionItemRepository;

	public TransactionItem create(Transaction tx, Product product) {
		TransactionItem transactionItem = TransactionItem.builder()
			.transaction(tx)
			.product(product)
			.build();
		return transactionItemRepository.save(transactionItem);
	}
}
