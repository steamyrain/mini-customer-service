package com.example.demo.dto.tx;

import java.time.Instant;

import com.example.demo.common.enums.PaymentMethodEnum;
import com.example.demo.common.enums.PaymentStatusEnum;

public interface ITxDTO {
	String customerId();
	Instant txTime();
	PaymentStatusEnum paymentStatus();
	PaymentMethodEnum paymentMethod();
}
