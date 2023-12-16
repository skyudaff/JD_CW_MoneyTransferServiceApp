package com.example.moneytransferserviceapp.repository;

import com.example.moneytransferserviceapp.dto.Card;
import com.example.moneytransferserviceapp.dto.TransferOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Repository
@RequiredArgsConstructor
public class TransferRepositoryImpl implements TransferRepository {
    private final Map<String, Card> cardStorage = new ConcurrentHashMap<>();

    {
        cardStorage.put("1234567890123456", new Card("1234567890123456", "12/23", "123"));
        cardStorage.put("9876543210987654", new Card("9876543210987654", "10/24", "321"));
    }

    private final Map<String, TransferOperation> transferStorage = new ConcurrentHashMap<>();

    @Override
    public Optional<Card> getCardByNumber(String cardNumber) {
        return Optional.ofNullable(cardStorage.get(cardNumber));
    }

    @Override
    public void addTransfer(TransferOperation transfer) {
        transferStorage.put(transfer.getOperationId(), transfer);
    }

    @Override
    public Optional<TransferOperation> getTransferByUuid(String uuid) {
        return Optional.ofNullable(transferStorage.get(uuid));
    }
}