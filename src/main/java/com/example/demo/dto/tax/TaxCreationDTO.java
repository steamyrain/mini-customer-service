package com.example.demo.dto.tax;

import java.math.BigDecimal;

public record TaxCreationDTO(String name, BigDecimal value) implements ITaxDTO {}
