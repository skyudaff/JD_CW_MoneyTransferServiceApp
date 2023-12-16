package com.example.moneytransferserviceapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferConfirmation {
    @NotBlank
    private String operationId;
    private String code;
}
