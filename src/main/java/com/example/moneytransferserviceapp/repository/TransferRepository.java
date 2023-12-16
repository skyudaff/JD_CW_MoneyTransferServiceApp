package com.example.moneytransferserviceapp.repository;

import com.example.moneytransferserviceapp.dto.Card;
import com.example.moneytransferserviceapp.dto.TransferOperation;

import java.util.Optional;

public interface TransferRepository {
    Optional<Card> getCardByNumber(String cardNumber);

    void addTransfer(TransferOperation transfer);

    Optional<TransferOperation> getTransferByUuid(String uuid);
}
