package com.example.demo.dto.product;

import java.math.BigDecimal;

public interface IProductGenReport {
	String getProductId();
	String getProductName();
	BigDecimal getTotalMoneySpentOnProduct();
}
