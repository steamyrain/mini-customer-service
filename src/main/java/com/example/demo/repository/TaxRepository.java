package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.dto.tax.ITaxGenReport;
import com.example.demo.entity.Tax;

public interface TaxRepository extends JpaRepository<Tax, String>{

	@Query(nativeQuery = true, value = "SELECT t.id as taxId, t.name as taxName, SUM(t.value*p.price*ti.amt) AS totalMoneySpentOnTax FROM tax t LEFT JOIN product_tax pt on pt.tax_id = t.id INNER JOIN product p on p.id = pt.product_id LEFT JOIN transaction_item ti on ti.product_id = p.id WHERE t.id = :taxId GROUP BY t.id, t.name")
	public ITaxGenReport generateReport(@Param("taxId")String taxId);
}
