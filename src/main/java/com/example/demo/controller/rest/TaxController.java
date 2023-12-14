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

import com.example.demo.dto.tax.TaxCreationDTO;
import com.example.demo.dto.tax.TaxGenReportDTO;
import com.example.demo.dto.tax.TaxUpdateDTO;
import com.example.demo.service.TaxService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tax")
public class TaxController {
	private final TaxService taxService;

	@GetMapping("/report")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<Object> generateReport(@ModelAttribute TaxGenReportDTO genReportDTO) {
		return ResponseEntity.ok(taxService.generateReport(genReportDTO));
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<Object> findById(@RequestParam(name = "id") String id) {
		return ResponseEntity.ok(taxService.findById(id));
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> create(@RequestBody TaxCreationDTO dto) {
		return ResponseEntity.ok(taxService.create(dto));
	}

	@PutMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> update(@RequestBody TaxUpdateDTO dto) {
		return ResponseEntity.ok(taxService.update(dto));
	}

	@DeleteMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> delete(@RequestParam(name = "id") String id) {
		taxService.delete(id);
		return ResponseEntity.ok(null);
	}

}
