package com.fakebank.dto;

import com.fakebank.model.Account;
import com.fakebank.model.SubTransaction;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class StatementResponseDto {
    private String accountNumber;
    private String accountType;
    private Float currentBalance;
    private Date lastUpdatedOn;
    private List<SubTransactionDto> transactions;

    public StatementResponseDto setStatementDtoResponse(List<SubTransactionDto> transactionList, Account account) {
        StatementResponseDto responseDto = new StatementResponseDto();
        responseDto.setAccountNumber(account.getAccountNumber());
        responseDto.setAccountType(account.getAccountType().getAccountType());
        responseDto.setCurrentBalance(account.getBalance());
        responseDto.setLastUpdatedOn(account.getLastUpdatedOn());
        responseDto.setTransactions(transactionList);
        return responseDto;
    }

}
