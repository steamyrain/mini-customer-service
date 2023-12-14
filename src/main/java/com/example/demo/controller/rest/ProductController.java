package com.example.demo.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.product.ProductCreationDTO;
import com.example.demo.dto.product.ProductGenReportDTO;
import com.example.demo.dto.product.ProductUpdateDTO;
import com.example.demo.service.ProductService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
	private final ProductService productService;

	@GetMapping("/report")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<Object> generateReport(@ModelAttribute ProductGenReportDTO genReportDTO) {
		return ResponseEntity.ok(productService.generateReport(genReportDTO));
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<Object> findById(@RequestParam(name = "id") String id) {
		return ResponseEntity.ok(productService.findById(id));
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> create(@RequestBody ProductCreationDTO dto) {
		return ResponseEntity.ok(productService.create(dto));
	}

	@PutMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> update(@RequestBody ProductUpdateDTO dto) {
		return ResponseEntity.ok(productService.update(dto));
	}

	@DeleteMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> delete(@RequestParam(name = "id") String id) {
		productService.delete(id);
		return ResponseEntity.ok(null);
	}
}
