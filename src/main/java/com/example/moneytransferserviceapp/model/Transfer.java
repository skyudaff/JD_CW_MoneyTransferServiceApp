package com.example.moneytransferserviceapp.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
@RequiredArgsConstructor
public class Transfer {
    private String uuid;
    private LocalDateTime dateTime;
    private String cardFromNumber;
    private String cardToNumber;
    private String cardFromValidTill;
    private Amount amount;
    private BigDecimal commission;
    private String result;


    @Override
    public String toString() {
        return String.format("%s From card: %s To card: %s %s Commission: %s  %s %s",
                getDateTime(), getCardFromNumber(), getCardToNumber(),
                getAmount(), getCommission(), getResult(), getUuid());
    }
}