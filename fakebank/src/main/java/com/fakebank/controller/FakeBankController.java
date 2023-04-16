package com.fakebank.controller;

import com.fakebank.dto.*;
import com.fakebank.model.Customer;
import com.fakebank.service.AccountService;
import com.fakebank.service.CustomerService;
import com.fakebank.utils.Constants;
import com.fakebank.utils.Utils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@Validated
public class FakeBankController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private AccountService accountService;

    @GetMapping(value = "/customers", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ResponseDto> getCustomers() {
        log.info("Get all customers request initiated.");
        List<Customer> customers = customerService.getCustomers();
        return ResponseEntity.ok().body(ResponseDto.success(customers, Constants.SUCCESS));
    }

    @PostMapping(value = "/accounts", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody AccountRequestDto accountRequestDto) {
        log.info("Request initiated to create account for custermer id : {} ", accountRequestDto.getCustomerId());
        String accountNumber = accountService.createAccount(accountRequestDto);
        return ResponseEntity.ok().body(ResponseDto.success("AccountNumber: "+ accountNumber, Constants.SUCCESS));
    }

    @GetMapping(value = "/accounts/{accountId}/balance", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ResponseDto> getAccountDetail(@Valid @PathVariable(name = "accountId") String accountNumber) {
        log.info("Request initiated to get account detail for account id : {} ", accountNumber);
        AccountResponseDto responseDto = accountService.getAccountDetail(accountNumber);
        return ResponseEntity.ok().body(ResponseDto.success(responseDto, Constants.SUCCESS));
    }

    @PostMapping(value = "/accounts/{accountNumber}/transactions", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ResponseDto> transferAmount(@Valid @RequestBody TransactionRequestDto transactionRequestDto,
                                                       @PathVariable(name="accountNumber") String accountNumber) {
        log.info("Request initiated for transaction for account : {} ", accountNumber);
        TransactionResponseDto responseDto = accountService.transferAmount(transactionRequestDto, accountNumber);
        if (responseDto.getStatus().equals(Constants.FAIL)) {
            return ResponseEntity.ok().body(ResponseDto.success(responseDto, Constants.TransactionFailMessage));
        }
        return ResponseEntity.accepted().body(ResponseDto.success(responseDto, Constants.TransactionSuccessMessage));
    }

    @GetMapping(value = "/accounts/{accountNumber}/transactions/statement", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ResponseDto> getStatement(@PathVariable(name="accountNumber") String accountNumber,
                                                    @RequestParam(name="fromDate") String fromDate,
                                                    @RequestParam(name="toDate") String toDate) {
        log.info("Request initiated for transaction history for account : {} ", accountNumber);
        Date startDate = Utils.convertStringToDate(fromDate);
        Date endDate = Utils.convertStringToDate(toDate);
        StatementResponseDto responseDto = accountService.getStatement(accountNumber, startDate, endDate);
        return ResponseEntity.ok().body(ResponseDto.success(responseDto, null));
    }
}
