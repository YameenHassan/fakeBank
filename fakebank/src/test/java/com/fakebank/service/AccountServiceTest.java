package com.fakebank.service;

import com.fakebank.dto.*;
import com.fakebank.exceptions.CustomNotFoundException;
import com.fakebank.model.*;
import com.fakebank.repository.*;
import com.fakebank.utils.TestUtils;
import com.fakebank.utils.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private BankBranchRepository bankBranchRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AccountTypeRepository accountTypeRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private TransactionTypeRepository transactionTypeRepository;

    @Test
    void testCreateAccountSuccess(){
        AccountRequestDto accountRequestDto = TestUtils.getMockAccountRequestDto();
        BankBranch branch = TestUtils.getMockBankBranch();
        Customer customer = TestUtils.getMockCustomer();
        AccountType accountType = TestUtils.getMockAccountType();
        Account account = TestUtils.getMockSaveAccount();

        when(bankBranchRepository.findById(accountRequestDto.getBranchId())).thenReturn(branch);
        when(customerRepository.findById(accountRequestDto.getCustomerId())).thenReturn(customer);
        when(accountTypeRepository.findById(accountRequestDto.getAccountTypeId())).thenReturn(accountType);
        when(accountRepository.save(any())).thenReturn(account);
        String accountNumber = accountService.createAccount(accountRequestDto);
        assertNotNull(accountNumber);
    }

    @Test
    void testGetAccountDetialSuccess(){
        String accountNumber = "4012-212518-1";
        Account account = TestUtils.getMockSaveAccount();
        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(account);
        assertDoesNotThrow(() -> accountService.getAccountDetail(accountNumber));
    }

    @Test
    void testGetAccountDetialFail(){
        String accountNumber = "4012-212518-1";
        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(null);
        assertThrows(CustomNotFoundException.class, () -> accountService.getAccountDetail(accountNumber));
    }

    @Test
    void testTransferAmountSuccess(){
        TransactionRequestDto transactionRequestDto = TestUtils.getTrasactionRequestDto();
        Account debitAccount = TestUtils.getMockSaveAccount();
        Account creditAccount = TestUtils.getMockSaveAccount();
        String accountNumber = "4012-608203-1";
        TransactionType transactionType = TestUtils.getMockTransactionType();
        when(accountRepository.findByAccountNumber(transactionRequestDto.getToAccountNumber())).thenReturn(creditAccount);
        when(accountRepository.findByAccountNumber(transactionRequestDto.getToAccountNumber())).thenReturn(debitAccount);
        when(transactionTypeRepository.findByTransactionType(transactionRequestDto.getTransactionType())).thenReturn(transactionType);
        when(accountRepository.save(debitAccount)).thenReturn(creditAccount);
        when(accountRepository.save(debitAccount)).thenReturn(debitAccount);

        TransactionResponseDto transferAmount = accountService.transferAmount(transactionRequestDto, accountNumber);
        assertNotNull(transferAmount);
        assertDoesNotThrow(() -> accountService.getAccountDetail(accountNumber));

    }

    @Test
    void testMockGetStatement(){

        Date fromDate = Utils.convertStringToDate("2023-01-16");
        Date toDate = Utils.convertStringToDate("2023-02-16");;
        Account account = TestUtils.getMockSaveAccount();
        String accountNumber = account.getAccountNumber();
        TransactionRequestDto transactionRequestDto = TestUtils.getTrasactionRequestDto();
        Set<Transaction> transactionList = TestUtils.getMockTransactionSet();

        when(accountRepository.findByAccountNumber(transactionRequestDto.getToAccountNumber())).thenReturn(account);
        when(transactionRepository.findByAccountNumberAndCreatedOnBetween(accountNumber, fromDate, toDate)).thenReturn(transactionList);

        StatementResponseDto statementResponseDto = accountService.getStatement(accountNumber, fromDate, toDate);
        assertNotNull(statementResponseDto);
        assertDoesNotThrow(() -> accountService.getAccountDetail(accountNumber));

    }

}
