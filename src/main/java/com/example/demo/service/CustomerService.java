package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.common.exception.NotFoundException;
import com.example.demo.dto.customer.CustomerCreationDTO;
import com.example.demo.dto.customer.CustomerUpdateDTO;
import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerService {
	private final CustomerRepository customerRepository;

	public Customer findById(String id) {
		Optional<Customer> customerOpt = customerRepository.findById(id);
		return customerOpt.orElseThrow(() -> new NotFoundException("customer with id: "+id+" not found"));
	}

	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	public Customer create(CustomerCreationDTO dto) {
		Customer customer = Customer.builder()
			.name(dto.name()).birthDate(dto.birthDate())
			.birthPlace(dto.birthPlace())
			.build();
		return customerRepository.save(customer);
	}

	public void delete(String id) {
		customerRepository.deleteById(id);
	}

	@Transactional
	public Customer update(CustomerUpdateDTO dto) {
		Optional<Customer> customerOpt = customerRepository.findById(dto.id());
		Customer customer = customerOpt.orElseThrow( () -> new NotFoundException("Customer with id: "+dto.id()+" not found"));
		customer.setName(dto.name());
		customer.setBirthDate(dto.birthDate());
		customer.setBirthPlace(dto.birthPlace());
		return customerRepository.save(customer);
	}
}
