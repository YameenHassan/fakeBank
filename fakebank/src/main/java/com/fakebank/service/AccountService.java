package com.fakebank.service;

import com.fakebank.dto.*;
import com.fakebank.model.Account;

import java.util.Date;

public interface AccountService {
    String createAccount(AccountRequestDto accountRequestDto);
    AccountResponseDto getAccountDetail(String accountNumber);
    TransactionResponseDto transferAmount(TransactionRequestDto transactionRequestDto, String accountNumber);
    StatementResponseDto getStatement(String accountNumber, Date fromDate, Date toDate);

}
