package com.example.demo.dto.tx;

import java.time.Instant;
import java.util.List;

import com.example.demo.common.enums.PaymentMethodEnum;
import com.example.demo.common.enums.PaymentStatusEnum;

public record TxCreationDTO(String customerId, List<TxItemCreationDTO> items, Instant txTime, PaymentMethodEnum paymentMethod, PaymentStatusEnum paymentStatus) implements ITxDTO {
}
