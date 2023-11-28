package com.example.moneytransferserviceapp.repository;

import com.example.moneytransferserviceapp.model.Card;
import com.example.moneytransferserviceapp.model.Transfer;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Repository
@RequiredArgsConstructor
public class TransferRepository {
    private final Map<String, Card> cardStorage = new ConcurrentHashMap<>();

    {
        cardStorage.put("1234567890123456", new Card("1234567890123456", "12/23", "123"));
        cardStorage.put("9876543210987654", new Card("9876543210987654", "10/24", "321"));
    }


    private final Map<String, Transfer> transferStorage = new ConcurrentHashMap<>();


    public Optional<Card> getCardByNumber(String cardNumber) {
        return Optional.ofNullable(cardStorage.get(cardNumber));
    }

    public void addTransfer(Transfer transfer) {
        try {
            if (transfer.getUuid() != null) {
                transferStorage.put(transfer.getUuid(), transfer);
                System.out.println("UUID added: " + transfer.getUuid());
            } else {
                throw new IllegalArgumentException("UUID cannot be null");
            }
        } catch (Exception e) {
            System.out.println("Error adding transfer: " + e.getMessage());
        }
    }

    public Optional<Transfer> getTransferByUuid(String uuid) {
        if (uuid == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(transferStorage.get(uuid));
    }
}