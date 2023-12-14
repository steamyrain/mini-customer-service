package com.example.demo.dto.tx;

import java.math.BigDecimal;
import java.time.Instant;

import com.example.demo.common.enums.PaymentMethodEnum;
import com.example.demo.common.enums.PaymentStatusEnum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TxSearchResponse {
	private String id;
	private String customerName;
	private Instant txTime;
	private BigDecimal nettAmtPaid;
	private BigDecimal totalAmtPaid;
	private BigDecimal totalTaxPaid;
	private PaymentStatusEnum paymentStatus;
	private PaymentMethodEnum paymentMethod;
	private String createdBy;
}
