package com.fakebank.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountRequestDto {
    @NotNull
    private Integer customerId;
    @NotNull
    private Float amount;
    @NotNull
    private Integer branchId;
    @NotNull
    private Integer accountTypeId;
    @NotNull
    private String currency;
    private Boolean isActive;
}
