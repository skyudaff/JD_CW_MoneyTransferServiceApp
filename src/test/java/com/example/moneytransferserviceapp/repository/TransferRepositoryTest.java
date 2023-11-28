package com.example.moneytransferserviceapp.repository;

import com.example.moneytransferserviceapp.model.Card;
import com.example.moneytransferserviceapp.model.Transfer;
import com.example.moneytransferserviceapp.repository.TransferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TransferRepositoryTest {
    @Mock
    private Map<String, Transfer> transferStorage;

    @InjectMocks
    private TransferRepository transferRepository;

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
        Transfer transfer = new Transfer();
        transfer.setUuid("123456");

        // Act
        transferRepository.addTransfer(transfer);

        // Assert
        assertEquals(transfer, transferRepository.getTransferByUuid("123456").orElse(null));
    }

    @Test
    void testGetTransferByUuid() {
        // Arrange
        String uuid = "transferUuid";
        Transfer expectedTransfer = new Transfer();
        expectedTransfer.setUuid(uuid);

        // Act
        Optional<Transfer> result = transferRepository.getTransferByUuid(uuid);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedTransfer, result.orElse(null));
    }

    @Test
    void testGetTransferByUuidNotFound() {
        // Arrange
        String uuid = "invalidUuid";

        // Act
        Optional<Transfer> result = transferRepository.getTransferByUuid(uuid);

        // Assert
        assertFalse(result.isPresent());
    }
}
