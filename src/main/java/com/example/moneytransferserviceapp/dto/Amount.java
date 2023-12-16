package com.example.moneytransferserviceapp.dto;

public record Amount(int value, String currency) {
    public long getValue() {
        return this.value;
    }
}
