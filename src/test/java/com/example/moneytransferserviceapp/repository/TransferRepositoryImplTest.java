package com.example.moneytransferserviceapp.repository;

import com.example.moneytransferserviceapp.dto.Card;
import com.example.moneytransferserviceapp.dto.TransferOperation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
public class TransferRepositoryImplTest {
    @InjectMocks
    private TransferRepositoryImpl transferRepository;

    @Test
    void testGetCardByNumber() {
        // Arrange
        String cardNumber = "1234567890123456";
        Card expectedCard = new Card(cardNumber, "12/23", "123");

        // Act
        Optional<Card> result = transferRepository.getCardByNumber(cardNumber);

        // Assert
        assertEquals(expectedCard, result.orElse(null));
    }

    @Test
    void testGetCardByNumberNotFound() {
        // Arrange
        String cardNumber = "0000111122223333";

        // Act
        Optional<Card> result = transferRepository.getCardByNumber(cardNumber);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testAddTransfer() {
        // Arrange
        TransferOperation transfer = new TransferOperation();
        transfer.setOperationId("123456");

        // Act
        transferRepository.addTransfer(transfer);

        // Assert
        assertEquals(transfer, transferRepository.getTransferByUuid("123456").orElse(null));
    }

    @Test
    void testGetTransferByUuidNotFound() {
        // Arrange
        String uuid = "invalidUuid";

        // Act
        Optional<TransferOperation> result = transferRepository.getTransferByUuid(uuid);

        // Assert
        assertFalse(result.isPresent());
    }
}
