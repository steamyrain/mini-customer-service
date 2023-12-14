package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ProductTax;

public interface ProductTaxRepository extends JpaRepository<ProductTax, String>{
}
