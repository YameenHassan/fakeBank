package com.fakebank.dto;

import lombok.Data;

@Data
public class TransactionRequestDto {
    private String toAccountNumber;
    private String transactionType;
    private Float amount;
}
