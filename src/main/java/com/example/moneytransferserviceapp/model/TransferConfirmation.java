package com.example.moneytransferserviceapp.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TransferConfirmation {
    @NotBlank
    private String operationId;
    @NotBlank
    private String code;
}
