package com.example.demo.dto.product;

import java.math.BigDecimal;

public record ProductCreationDTO(String name, BigDecimal price) implements IProductDTO {
}
