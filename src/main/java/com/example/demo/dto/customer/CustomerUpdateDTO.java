package com.example.demo.dto.customer;

import java.time.Instant;

public record CustomerUpdateDTO(String id, String name, Instant birthDate, String birthPlace) implements ICustomerDTO{}
