package com.example.demo.dto.tax;

import java.math.BigDecimal;

public interface ITaxGenReport {
	String getTaxId();
	String getTaxName();
	BigDecimal getTotalMoneySpentOnTax();
}
