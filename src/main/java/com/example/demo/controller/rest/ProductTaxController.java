package com.example.demo.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.producttax.ProductTaxCreationDTO;
import com.example.demo.dto.producttax.ProductTaxUpdateDTO;
import com.example.demo.service.ProductTaxService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/product-tax")
public class ProductTaxController {
	private final ProductTaxService productTaxService;

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<Object> findById(@RequestParam(name = "id") String id) {
		return ResponseEntity.ok(productTaxService.findById(id));
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> create(@RequestBody ProductTaxCreationDTO dto) {
		return ResponseEntity.ok(productTaxService.create(dto));
	}

	@PutMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> update(@RequestBody ProductTaxUpdateDTO dto) {
		return ResponseEntity.ok(productTaxService.update(dto));
	}

	@DeleteMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> delete(@RequestParam(name = "id") String id) {
		productTaxService.delete(id);
		return ResponseEntity.ok(null);
	}
}
