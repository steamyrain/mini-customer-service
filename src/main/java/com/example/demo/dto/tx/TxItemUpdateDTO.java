package com.example.demo.dto.tx;

import com.example.demo.dto.txitem.ITxItemDTO;

public record TxItemUpdateDTO (String txItemId, String productId, Integer amount) implements ITxItemDTO {
}
