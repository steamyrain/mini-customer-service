package com.example.demo.dto.tx;

import java.time.Instant;
import java.util.List;

import com.example.demo.common.enums.PaymentMethodEnum;
import com.example.demo.common.enums.PaymentStatusEnum;

public record TxUpdateDTO(String id, String customerId, List<TxItemUpdateDTO> items, PaymentStatusEnum paymentStatus, PaymentMethodEnum paymentMethod, Instant txTime) implements ITxDTO {
}
