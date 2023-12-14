package com.example.demo.dto.txitem;

public record TxItemCreationDTO (String txId, String productId, Integer amount) implements ITxItemDTO {
}
