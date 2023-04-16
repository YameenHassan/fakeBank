package com.fakebank.dto;

import com.fakebank.model.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@Builder
public class TransactionResponseDto {
    private String fromAccountNumber;
    private String toAccountNumber;
    private String transactionType;
    private Float transferAmount;
    private Float currentBalance;
    private Date date;
    private Integer referenceNumber;
    private String status;

}
