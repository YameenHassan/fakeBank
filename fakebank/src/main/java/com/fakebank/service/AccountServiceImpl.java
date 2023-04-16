package com.fakebank.service;

import com.fakebank.dto.*;
import com.fakebank.exceptions.CustomNotFoundException;
import com.fakebank.model.*;
import com.fakebank.repository.*;
import com.fakebank.utils.Constants;
import com.fakebank.utils.Utils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BankBranchRepository bankBranchRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountTypeRepository accountTypeRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

    @Override
    public String createAccount(AccountRequestDto accountRequestDto) {

        BankBranch branch = bankBranchRepository.findById(accountRequestDto.getBranchId());
        ifNullThrowNotFoundException(branch, "Branch not found with id  : " + accountRequestDto.getBranchId());

        Account account = new Account();
        account.setAccountNumber(Utils.generateAccountNumber(branch.getBranchCode(), accountRequestDto.getAccountTypeId()));
        account.setBalance(accountRequestDto.getAmount());
        Customer customer = customerRepository.findById(accountRequestDto.getCustomerId());
        ifNullThrowNotFoundException(customer, "Customer not found with id  : " + accountRequestDto.getCustomerId());

        account.setCustomer(customer);
        account.setBankBranch(branch);
        AccountType accountType = accountTypeRepository.findById(accountRequestDto.getAccountTypeId());
        ifNullThrowNotFoundException(accountType, "AccountType not found with id  : " + accountRequestDto.getAccountTypeId());

        account.setAccountType(accountType);
        account.setCurrency(accountRequestDto.getCurrency());
        Date date = Utils.getCurrentDateTime();
        account.setCreatedOn(date);
        account.setLastUpdatedOn(date);
        account.setIsActive(Utils.isNotNullOrEmpty(accountRequestDto.getIsActive() ? accountRequestDto.getIsActive() : true));
        Account createdAccount = accountRepository.save(account);
        return createdAccount.getAccountNumber();
    }

    @Override
    public AccountResponseDto getAccountDetail(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        ifNullThrowNotFoundException(account, "Account not found with number : " + accountNumber);

        AccountResponseDto responseDto = new AccountResponseDto();
        return responseDto.setAccountResponse(account);
    }

    @Override
    @Transactional
    public TransactionResponseDto transferAmount(TransactionRequestDto transactionRequestDto, String accountNumber) {

        Account debitAccount = accountRepository.findByAccountNumber(accountNumber);
        ifNullThrowNotFoundException(debitAccount, "Account not found with number : " + accountNumber);
        Account creditAccount = accountRepository.findByAccountNumber(transactionRequestDto.getToAccountNumber());
        ifNullThrowNotFoundException(creditAccount, "Account not found with number : " + transactionRequestDto.getToAccountNumber());

        Float amount = transactionRequestDto.getAmount();
        TransactionType transactionType = transactionTypeRepository.findByTransactionType(transactionRequestDto.getTransactionType());
        ifNullThrowNotFoundException(transactionType, "TransactionType not found with id : " + transactionRequestDto.getTransactionType());

        Integer referenceId = Utils.generateReferenceId();
        Date transactionDate = Utils.getCurrentDateTime();
        TransactionResponseDto responseDto;
        String status = Constants.FAIL;

        if(debitAccount.getBalance() >= amount){
            debitAccount.setBalance(debitAccount.getBalance() - amount);
            debitAccount.setLastUpdatedOn(Utils.getCurrentDateTime());
            creditAccount.setBalance(creditAccount.getBalance() + amount);
            creditAccount.setLastUpdatedOn(transactionDate);
            accountRepository.save(debitAccount);
            accountRepository.save(creditAccount);

            saveTransaction(debitAccount, amount, transactionType, referenceId, Constants.DEBIT, transactionDate, debitAccount.getBalance());
            saveTransaction(creditAccount, amount, transactionType, referenceId, Constants.CREDIT, transactionDate, creditAccount.getBalance());

            status = Constants.SUCCESS;
        }

        responseDto = TransactionResponseDto.builder()
                .fromAccountNumber(debitAccount.getAccountNumber())
                .toAccountNumber(creditAccount.getAccountNumber())
                .transactionType(transactionType.getTransactionType())
                .transferAmount(amount)
                .currentBalance(debitAccount.getBalance())
                .referenceNumber(status.equals(Constants.SUCCESS) ? referenceId : null)
                .status(status)
                .date(transactionDate)
                .build();

        return responseDto;
    }

    @Override
    public StatementResponseDto getStatement(String accountNumber, Date fromDate, Date toDate) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        ifNullThrowNotFoundException(account, "Account not found with number : " + accountNumber);

        StatementResponseDto statementDto = new StatementResponseDto();
        Set<Transaction> transactionList = transactionRepository.findByAccountNumberAndCreatedOnBetween(accountNumber, fromDate, toDate);
        List<SubTransactionDto> subTransactionDtoList = new ArrayList<>();

        for (Transaction transaction : transactionList) {
            SubTransactionDto subTransactionDto = new SubTransactionDto();
            subTransactionDtoList.add(subTransactionDto.setSubTransactionResponse(transaction.getTransaction(), accountNumber));
        }
        return statementDto.setStatementDtoResponse(subTransactionDtoList, account);
    }

    private void saveTransaction(Account account, Float amount, TransactionType type,
                                 Integer referenceId, String debitOrCredit, Date date, Float currentBalance){
        Transaction transaction = new Transaction();
        transaction.setAccountNumber(account.getAccountNumber());
        transaction.setAmount(amount);
        transaction.setTransactionType(type);
        transaction.setReferenceId(referenceId);
        transaction.setCreditDebit(debitOrCredit);
        transaction.setCurrentBalance(currentBalance);
        transaction.setCreatedOn(date);
        transactionRepository.save(transaction);
    }

    private void ifNullThrowNotFoundException(Object object, String message){
        if (Utils.isNullOrEmpty(object)) {
            throw new CustomNotFoundException(message);
        }
    }
}
