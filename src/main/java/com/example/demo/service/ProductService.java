package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.common.exception.NotFoundException;
import com.example.demo.dto.product.IProductGenReport;
import com.example.demo.dto.product.ProductCreationDTO;
import com.example.demo.dto.product.ProductGenReportDTO;
import com.example.demo.dto.product.ProductUpdateDTO;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {

	private final ProductRepository productRepository; 

	public Product findById(String id) {
		Optional<Product> productOpt = productRepository.findById(id);
		return productOpt.orElseThrow(() -> new NotFoundException("Product with id: "+id+" not found"));
	}

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public Product create(ProductCreationDTO dto) {
		Product product = Product.builder()
			.name(dto.name())
			.price(dto.price())
			.build();
		return productRepository.save(product);
	}

	public void delete(String id) {
		productRepository.deleteById(id);
	}

	@Transactional
	public Product update(ProductUpdateDTO dto) {
		Optional<Product> productOpt = productRepository.findById(dto.id());
		Product product = productOpt.orElseThrow( () -> new NotFoundException("Product with id: "+dto.id()+" not found"));
		product.setName(dto.name());
		product.setPrice(dto.price());
		return productRepository.save(product);
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public IProductGenReport generateReport(ProductGenReportDTO genReportDTO) {
		return productRepository.generateReport(genReportDTO.productId());
	}
}
