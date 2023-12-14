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

import com.example.demo.dto.tx.ITxGenCusReportBetween;
import com.example.demo.dto.tx.TxCreationDTO;
import com.example.demo.dto.tx.TxGenReportDTO;
import com.example.demo.dto.tx.TxSearchDTO;
import com.example.demo.dto.tx.TxUpdateDTO;
import com.example.demo.service.TransactionService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/transaction")
public class TransactionController {
	private final TransactionService txService;

	@GetMapping("/search")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<Object> search(@ModelAttribute TxSearchDTO searchDTO) {
		return ResponseEntity.ok(txService.search(searchDTO));
	}

	@GetMapping("/report")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<Object> generateReport(@ModelAttribute TxGenReportDTO genReportDTO) {
		ITxGenCusReportBetween res = txService.generateReport(genReportDTO);
		log.info("{}", res);
		return ResponseEntity.ok(res);
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<Object> findById(@RequestParam(name = "id") String id) {
		return ResponseEntity.ok(txService.findById(id));
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<Object> create(@RequestBody TxCreationDTO dto) {
		return ResponseEntity.ok(txService.create(dto));
	}

	@PutMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> update(@RequestBody TxUpdateDTO dto) {
		return ResponseEntity.ok(txService.update(dto));
	}

	@DeleteMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> delete(@RequestParam(name = "id") String id) {
		txService.delete(id);
		return ResponseEntity.ok(null);
	}

}
