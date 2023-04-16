package com.fakebank.dto;

import com.fakebank.model.SubTransaction;
import com.fakebank.utils.Constants;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SubTransactionDto {
    private String creditAccountNumber;
    private String debitAccountNumber;
    private String transactionType;
    private Float amount;
    private String creditOrDebit;
    private Date date;
    private Float balance;
    private Integer referenceNumber;

    public SubTransactionDto setSubTransactionResponse(List<SubTransaction> transactionList, String accountNumber) {
        SubTransactionDto subTransactionDto = new SubTransactionDto();
        for (SubTransaction sub : transactionList) {

            if (sub.getCreditDebit().equals(Constants.CREDIT)) {
                subTransactionDto.setCreditAccountNumber(sub.getAccountNumber());
            } else if (sub.getCreditDebit().equals(Constants.DEBIT)) {
                subTransactionDto.setDebitAccountNumber(sub.getAccountNumber());
            }
            if (sub.getAccountNumber().equals(accountNumber)) {
                subTransactionDto.setCreditOrDebit(sub.getCreditDebit());
                subTransactionDto.setBalance(sub.getCurrentBalance());
                subTransactionDto.setTransactionType(sub.getTransactionType().getTransactionType());
                subTransactionDto.setAmount(sub.getAmount());
                subTransactionDto.setDate(sub.getCreatedOn());
                subTransactionDto.setReferenceNumber(sub.getReferenceId());
            }
        }
        return subTransactionDto;
    }
}
