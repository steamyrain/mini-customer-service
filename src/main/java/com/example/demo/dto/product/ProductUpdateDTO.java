package com.example.demo.dto.product;

import java.math.BigDecimal;

public record ProductUpdateDTO(String id, String name, BigDecimal price) implements IProductDTO {
}
