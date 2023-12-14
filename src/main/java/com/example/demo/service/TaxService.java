package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.common.exception.NotFoundException;
import com.example.demo.dto.tax.ITaxGenReport;
import com.example.demo.dto.tax.TaxCreationDTO;
import com.example.demo.dto.tax.TaxGenReportDTO;
import com.example.demo.dto.tax.TaxUpdateDTO;
import com.example.demo.entity.Tax;
import com.example.demo.repository.TaxRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TaxService {

	private final TaxRepository taxRepository; 

	public Tax findById(String id) {
		Optional<Tax> taxOpt = taxRepository.findById(id);
		return taxOpt.orElseThrow(() -> new NotFoundException("Tax with id: "+id+" not found"));
	}

	public List<Tax> findAll() {
		return taxRepository.findAll();
	}

	public Tax create(TaxCreationDTO dto) {
		Tax tax = Tax.builder()
			.name(dto.name())
			.value(dto.value())
			.build();
		return taxRepository.save(tax);
	}

	public void delete(String id) {
		taxRepository.deleteById(id);
	}

	@Transactional
	public Tax update(TaxUpdateDTO dto) {
		Optional<Tax> taxOpt = taxRepository.findById(dto.id());
		Tax tax = taxOpt.orElseThrow( () -> new NotFoundException("Tax with id: "+dto.id()+" not found"));
		tax.setName(dto.name());
		tax.setValue(dto.value());
		return taxRepository.save(tax);
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public ITaxGenReport generateReport(TaxGenReportDTO genReportDTO) {
		return taxRepository.generateReport(genReportDTO.taxId());
	}

}
