package com.example.demo.dto.tx;

import java.math.BigDecimal;

public interface ITxGenCusReportBetween {
	String getCustomerName();
	String getCustomerId();
	BigDecimal getSumTxTotalAmtPaid();
}
