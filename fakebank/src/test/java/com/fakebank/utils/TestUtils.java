package com.fakebank.utils;

import com.fakebank.dto.AccountRequestDto;
import com.fakebank.dto.TransactionRequestDto;
import com.fakebank.model.*;

import java.util.*;

public class TestUtils {

    public static AccountRequestDto getMockAccountRequestDto(){
        AccountRequestDto account = new AccountRequestDto();
        account.setCustomerId(1);
        account.setAmount(10.5f);
        account.setBranchId(1);
        account.setAccountTypeId(1);
        account.setCurrency("PKR");
        account.setIsActive(true);
        return account;
    }

    public static BankBranch getMockBankBranch(){
        BankBranch branch = new BankBranch();
        branch.setId(1);
        branch.setBranchCode(1234);
        branch.setBank(getMockBank());
        branch.setAddress("Karachi");
        branch.setIsActive(true);
        return branch;
    }

    public static Bank getMockBank(){
        Bank bank = new Bank();
        bank.setId(1);
        bank.setBankName("Standard Chartered");
        bank.setBankCode(1234);
        bank.setIsActive(true);
        return bank;
    }

    public static Customer getMockCustomer(){
        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirstName("Arisha");
        customer.setLastName("Barron");
        customer.setNationalId("1234-1234-0001");
        customer.setEmail("arisa@gmail.com");
        customer.setPhone("12345678");
        customer.setAddress("Karachi");
        customer.setIsActive(true);
        return customer;
    }

    public static AccountType getMockAccountType(){
        AccountType accountType = new AccountType();
        accountType.setId(1);
        accountType.setAccountType("current");
        return accountType;
    }

    public static Account getMockSaveAccount(){

        BankBranch branch = getMockBankBranch();
        AccountRequestDto accountRequestDto = getMockAccountRequestDto();
        Account account = new Account();
        account.setId(1);
        account.setAccountNumber("4012-608203-1");
        account.setBalance(accountRequestDto.getAmount());
        Customer customer = getMockCustomer();
        account.setCustomer(customer);
        account.setBankBranch(branch);
        AccountType accountType = getMockAccountType();
        account.setAccountType(accountType);
        account.setCurrency(accountRequestDto.getCurrency());
        Date date = Utils.getCurrentDateTime();
        account.setCreatedOn(date);
        account.setLastUpdatedOn(date);
        account.setIsActive(Utils.isNotNullOrEmpty(accountRequestDto.getIsActive() ? accountRequestDto.getIsActive() : true));
        return account;
    }

    public static TransactionRequestDto getTrasactionRequestDto(){
        TransactionRequestDto requestDto = new TransactionRequestDto();
        requestDto.setToAccountNumber("4012-608203-1");
        requestDto.setTransactionType("transfer");
        requestDto.setAmount(10.0f);
        return requestDto;
    }

    public static TransactionType getMockTransactionType(){
        TransactionType transactionType = new TransactionType();
        transactionType.setId(1);
        transactionType.setTransactionType("transfer");
        return transactionType;
    }

    public static Set<Transaction> getMockTransactionSet(){
        Transaction transaction = new Transaction();
        transaction.setId(1);
        transaction.setAccountNumber("4012-212518-1");
        transaction.setAmount(10.0f);
        TransactionType transactionType = getMockTransactionType();
        transaction.setTransactionType(transactionType);
        transaction.setReferenceId(70840608);
        transaction.setCreditDebit("debit");
        transaction.setCurrentBalance(10.0f);
        transaction.setCreatedOn(Utils.convertStringToDate("2023-04-16"));

        List<SubTransaction> subTransactionList = new ArrayList<>();
        subTransactionList.add(getMockSubTransaction("4012-212518-1", 1, "debit"));
        subTransactionList.add(getMockSubTransaction("4012-608203-1", 2, "credit"));
        transaction.setTransaction(subTransactionList);

        Set<Transaction> transactionSet = new HashSet<>();
        transactionSet.add(transaction);
        return transactionSet;
    }

    public static SubTransaction getMockSubTransaction(String accountNumber, Integer id, String debitOrCredit){
        SubTransaction subTransaction = new SubTransaction();
        subTransaction.setId(id);
        subTransaction.setAccountNumber(accountNumber);
        subTransaction.setAmount(10.0f);
        TransactionType transactionType = getMockTransactionType();
        subTransaction.setTransactionType(transactionType);
        subTransaction.setReferenceId(70840608);
        subTransaction.setCreditDebit(debitOrCredit);
        subTransaction.setCreatedOn(Utils.convertStringToDate("2023-04-16"));
        return subTransaction;
    }

}
