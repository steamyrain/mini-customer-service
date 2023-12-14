package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.dto.product.IProductGenReport;
import com.example.demo.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String>{

	@Query(nativeQuery = true, value = "SELECT p.id as productId, p.name as productName, SUM(ti.amt)*p.price AS totalMoneySpentOnProduct FROM product p LEFT JOIN transaction_item ti on ti.product_id = p.id WHERE p.id = :productId GROUP BY p.id, p.name")
	public IProductGenReport generateReport(@Param("productId") String productId);
	}
