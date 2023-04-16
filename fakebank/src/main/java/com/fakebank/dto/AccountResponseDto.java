package com.fakebank.dto;

import com.fakebank.model.Account;
import lombok.Builder;
import lombok.Data;

@Data
public class AccountResponseDto {

    private String accountNumber;
    private Float balance;
    private String accountType;
    private Boolean isActive;
    private CustomerDetail customer;
    private BankDetail bank;


    @Builder
    @Data
    private static class CustomerDetail{
        private Integer customerId;
        private String FullName;
    }


    @Builder
    @Data
    private static class BankDetail{
        private String bankName;
        private Integer branchCode;
    }

    public AccountResponseDto setAccountResponse(Account account){
        CustomerDetail customerDetail = CustomerDetail.builder()
                .customerId(account.getCustomer().getId())
                .FullName(account.getCustomer().getFirstName() + " " + account.getCustomer().getFirstName())
                .build();
        BankDetail bankDetail = BankDetail.builder()
                .bankName(account.getBankBranch().getBank().getBankName())
                .branchCode(account.getBankBranch().getBranchCode())
                .build();

        AccountResponseDto responseDto = new AccountResponseDto();
        responseDto.setAccountNumber(account.getAccountNumber());
        responseDto.setBalance(account.getBalance());
        responseDto.setAccountType(account.getAccountType().getAccountType());
        responseDto.setIsActive(account.getIsActive());
        responseDto.setCustomer(customerDetail);
        responseDto.setBank(bankDetail);

        return responseDto;
    }
}
