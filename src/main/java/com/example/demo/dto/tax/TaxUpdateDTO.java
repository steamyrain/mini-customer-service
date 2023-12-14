package com.example.demo.dto.tax;

import java.math.BigDecimal;

public record TaxUpdateDTO(String id, String name, BigDecimal value) implements ITaxDTO {}
