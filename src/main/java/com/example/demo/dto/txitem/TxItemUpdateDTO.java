package com.example.demo.dto.txitem;

public record TxItemUpdateDTO (String id, String txId, String productId, Integer amount) implements ITxItemDTO {
}
