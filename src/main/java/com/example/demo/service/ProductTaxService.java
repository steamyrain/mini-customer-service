package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.common.exception.NotFoundException;
import com.example.demo.dto.producttax.ProductTaxCreationDTO;
import com.example.demo.dto.producttax.ProductTaxUpdateDTO;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductTax;
import com.example.demo.entity.Tax;
import com.example.demo.repository.ProductTaxRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductTaxService {

	private final ProductTaxRepository productTaxRepository; 
	private final ProductService productService;
	private final TaxService taxService;

	public ProductTax findById(String id) {
		Optional<ProductTax> productTaxOpt = productTaxRepository.findById(id);
		return productTaxOpt.orElseThrow(() -> new NotFoundException("ProductTax with id: "+id+" not found"));
	}

	public List<ProductTax> findAll() {
		return productTaxRepository.findAll();
	}

	@Transactional
	public ProductTax create(ProductTaxCreationDTO dto) {
		Product product = productService.findById(dto.productId());
		Tax tax = taxService.findById(dto.taxId());
		ProductTax productTax = ProductTax.builder()
			.product(product)
			.tax(tax)
			.build();
		return productTaxRepository.save(productTax);
	}

	public void delete(String id) {
		productTaxRepository.deleteById(id);
	}

	@Transactional
	public ProductTax update(ProductTaxUpdateDTO dto) {
		Optional<ProductTax> productTaxOpt = productTaxRepository.findById(dto.id());
		ProductTax productTax = productTaxOpt.orElseThrow( () -> new NotFoundException("ProductTax with id: "+dto.id()+" not found"));
		Product product = productService.findById(dto.productId());
		Tax tax = taxService.findById(dto.taxId());
		productTax.setProduct(product);
		productTax.setTax(tax);
		return productTaxRepository.save(productTax);
	}
}
