package com.example.demo.dto.tx;

import java.time.Instant;

import org.springframework.lang.NonNull;

public record TxGenReportDTO(@NonNull String customerId, Instant dateStart, Instant dateEnd){ 
}
