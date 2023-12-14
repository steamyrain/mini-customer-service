package com.example.demo.dto.customer;

import java.time.Instant;

public interface ICustomerDTO {
	String name();
	String birthPlace();
	Instant birthDate();
}
