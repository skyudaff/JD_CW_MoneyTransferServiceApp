package com.example.moneytransferserviceapp.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public class Amount {
    @NotNull
    private Integer value;
    @NotBlank
    private String currency;
}
