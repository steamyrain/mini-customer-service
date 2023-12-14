package com.example.demo.dto.tx;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Sort;

import com.example.demo.common.enums.PaymentMethodEnum;
import com.example.demo.common.enums.PaymentStatusEnum;

public record TxSearchDTO(String name, Instant dateStart, Instant dateEnd, List<PaymentStatusEnum> paymentStatus, List<PaymentMethodEnum> paymentMethod,Sort.Direction direction, String createdBy) {
}
