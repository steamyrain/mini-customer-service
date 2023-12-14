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

import com.example.demo.dto.customer.CustomerCreationDTO;
import com.example.demo.dto.customer.CustomerUpdateDTO;
import com.example.demo.service.CustomerService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/customer")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
public class CustomerController {
	private final CustomerService customerService;

	@GetMapping
	public ResponseEntity<Object> findById(@RequestParam(name = "id") String id) {
		return ResponseEntity.ok(customerService.findById(id));
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody CustomerCreationDTO dto) {
		return ResponseEntity.ok(customerService.create(dto));
	}

	@PutMapping
	public ResponseEntity<Object> update(@RequestBody CustomerUpdateDTO dto) {
		return ResponseEntity.ok(customerService.update(dto));
	}

	@DeleteMapping
	public ResponseEntity<Object> delete(@RequestParam(name = "id") String id) {
		customerService.delete(id);
		return ResponseEntity.ok(null);
	}
}
