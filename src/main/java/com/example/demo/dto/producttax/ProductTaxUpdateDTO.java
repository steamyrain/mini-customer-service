package com.example.demo.dto.producttax;

public record ProductTaxUpdateDTO(String id, String productId, String taxId) implements IProductTaxDTO {
}
