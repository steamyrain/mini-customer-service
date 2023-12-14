package com.example.demo.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.common.exception.NotFoundException;
import com.example.demo.dto.tx.ITxGenCusReportBetween;
import com.example.demo.dto.tx.TxCreationDTO;
import com.example.demo.dto.tx.TxGenReportDTO;
import com.example.demo.dto.tx.TxItemCreationDTO;
import com.example.demo.dto.tx.TxItemUpdateDTO;
import com.example.demo.dto.tx.TxSearchDTO;
import com.example.demo.dto.tx.TxSearchResponse;
import com.example.demo.dto.tx.TxUpdateDTO;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.entity.Transaction;
import com.example.demo.entity.TransactionItem;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.TransactionItemRepository;
import com.example.demo.repository.TransactionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionService {
	private final TransactionRepository transactionRepository;
	private final TransactionItemRepository transactionItemRepository;
	private final ProductRepository productRepository;
	private final CustomerRepository customerRepository;

	public Transaction findById(String id) {
		Optional<Transaction> txOpt = transactionRepository.findById(id);
		return txOpt.orElseThrow(() -> new NotFoundException("Transaction with id: "+id+" not found"));
	}

	@Transactional
	public Transaction create(TxCreationDTO dto) {
		List<TxItemCreationDTO> items = dto.items();
		Map<String, Integer> productMap = new HashMap<>();
		items.parallelStream().forEach(i -> productMap.put(i.productId(),i.amount()));
		List<Product> products = productRepository.findAllById(productMap.keySet());
		BigDecimal nettAmtPaid = products.stream()
			.map(p -> p.getPrice().multiply(BigDecimal.valueOf(productMap.get(p.getId()))))
			.reduce(BigDecimal.ZERO, (partial, current) -> partial.add(current));
		BigDecimal totalTaxPaid = products.stream()
			.filter(p -> Objects.nonNull(p.getProductTaxes()))
			.map(p -> p.getProductTaxes()
				.stream()
				.map(t -> t.getTax().getValue())
				.reduce(BigDecimal.ZERO, 
					(partial, current) -> partial.add(p.getPrice()
						.multiply(BigDecimal.valueOf(productMap.get(p.getId())))
						.multiply(current)
					)
				)
			).reduce(BigDecimal.ZERO,(partial, current) -> partial.add(current));
		Customer customer = customerRepository.findById(dto.customerId())
			.orElseThrow(() -> new NotFoundException("customer with id: "+dto.customerId()+" not found"));
		Transaction tx = Transaction.builder()
			.customer(customer)
			.nettAmountPaid(nettAmtPaid)
			.txTime(Instant.now())
			.totalTaxPaid(totalTaxPaid)
			.totalAmountPaid(nettAmtPaid.add(totalTaxPaid))
			.paymentStatus(dto.paymentStatus())
			.paymentMethod(dto.paymentMethod())
			.build();
		tx = transactionRepository.saveAndFlush(tx);
		List<TransactionItem> transactionItems = new LinkedList<>();
		for (Product product : products) {
			transactionItems.add(
				TransactionItem.builder()
					.product(product)
					.transaction(tx)
					.amount(productMap.get(product.getId()))
					.build()
			);
		}
		transactionItemRepository.saveAll(transactionItems);
		return tx;
	}

	private record ProductAmt(Product product, Integer amount) {}

	@Transactional
	public Transaction update(TxUpdateDTO dto) {
		Transaction tx = findById(dto.id());
		Customer customer = customerRepository.findById(dto.customerId())
			.orElseThrow(() -> new NotFoundException("customer with id: "+dto.customerId()+" not found"));
		Map<String, ProductAmt> txItemIdMap = new HashMap<>();
		Map<String, Integer> productMap = new HashMap<>();

		for(TxItemUpdateDTO i : dto.items()) {
			txItemIdMap.put(i.txItemId(), new ProductAmt(productRepository.findById(i.productId()).orElseThrow(() -> new NotFoundException("product with id: "+i.productId()+" not found")), i.amount()));
			productMap.put(i.productId(), i.amount());
		}

		List<TransactionItem> txItems = transactionItemRepository.findAllById(txItemIdMap.keySet());	
		txItems.stream().forEach(t -> {
			t.setAmount(txItemIdMap.get(t.getId()).amount());
			t.setProduct(txItemIdMap.get(t.getId()).product());
		});

		List<Product> products = txItemIdMap.values().stream().map(t -> t.product()).toList();

		BigDecimal nettAmtPaid = products.stream()
			.map(p -> p.getPrice().multiply(BigDecimal.valueOf(productMap.get(p.getId()))))
			.reduce(BigDecimal.ZERO, (partial, current) -> partial.add(current));

		BigDecimal totalTaxPaid = products.stream()
			.filter(p -> Objects.nonNull(p.getProductTaxes()))
			.map(p -> p.getProductTaxes()
				.stream()
				.map(t -> t.getTax().getValue())
				.reduce(BigDecimal.ZERO, 
					(partial, current) -> partial.add(p.getPrice()
						.multiply(BigDecimal.valueOf(productMap.get(p.getId())))
						.multiply(current)
					)
				)
			).reduce(BigDecimal.ZERO,(partial, current) -> partial.add(current));
		
		tx.setCustomer(customer);
		tx.setTotalTaxPaid(totalTaxPaid);
		tx.setNettAmountPaid(nettAmtPaid);
		tx.setTotalAmountPaid(nettAmtPaid.add(totalTaxPaid));
		tx.setTxTime(dto.txTime());
		tx.setPaymentMethod(dto.paymentMethod());
		tx.setPaymentStatus(dto.paymentStatus());
		transactionRepository.saveAndFlush(tx);
		transactionItemRepository.saveAllAndFlush(txItems);

		return tx;
	}

	public void delete(String id) {
		transactionRepository.deleteById(id);
	}

	public List<TxSearchResponse> search(TxSearchDTO searchDTO) {
		return transactionRepository.searchWithFilters(searchDTO);
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public ITxGenCusReportBetween generateReport(TxGenReportDTO genReportDTO) {
		if(Objects.nonNull(genReportDTO.dateStart())
			&& Objects.nonNull(genReportDTO.dateEnd())
			&& genReportDTO.dateStart().compareTo(genReportDTO.dateEnd()) < 0
		){
			return transactionRepository.genCusReportBetween(genReportDTO.customerId(), genReportDTO.dateStart(), genReportDTO.dateEnd());
		} else if(Objects.isNull(genReportDTO.dateStart()) && Objects.isNull(genReportDTO.dateEnd())) {
			return transactionRepository.genCusReport(genReportDTO.customerId());
		}
		return null;
	}

}
