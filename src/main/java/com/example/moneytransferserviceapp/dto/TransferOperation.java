package com.example.moneytransferserviceapp.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode
public class TransferOperation {
    private String operationId;
    private LocalDateTime dateTime;
    private String cardFromNumber;
    private String cardToNumber;
    private String cardFromValidTill;
    private Amount amount;
    private BigDecimal commission;
    private String result;
}
