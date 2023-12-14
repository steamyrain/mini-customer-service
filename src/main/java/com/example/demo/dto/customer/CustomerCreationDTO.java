package com.example.demo.dto.customer;

import java.time.Instant;

public record CustomerCreationDTO(String name, Instant birthDate, String birthPlace) implements ICustomerDTO{}
