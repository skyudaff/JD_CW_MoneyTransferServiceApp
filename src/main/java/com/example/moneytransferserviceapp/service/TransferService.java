package com.example.moneytransferserviceapp.service;


import com.example.moneytransferserviceapp.exception.ErrorInputDataException;
import com.example.moneytransferserviceapp.exception.ErrorTransferException;
import com.example.moneytransferserviceapp.logger.TransferLogger;
import com.example.moneytransferserviceapp.model.Card;
import com.example.moneytransferserviceapp.model.Transfer;
import com.example.moneytransferserviceapp.model.TransferConfirmation;
import com.example.moneytransferserviceapp.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferService {
    @Value("${verify.code}")
    private String verifyCode;

    @Value("${transfer.commission}")
    private BigDecimal commission;

    private final TransferRepository transferRepository;
    private final TransferLogger transferLogger;


    public Transfer operation(Transfer transfer) {
        try {
            validateCardExpiry(transfer.getCardFromValidTill());

            Optional<Card> cardFromOptional = transferRepository
                    .getCardByNumber(transfer.getCardFromNumber());
            Optional<Card> cardToOptional = transferRepository
                    .getCardByNumber(transfer.getCardToNumber());

            if (cardFromOptional.isPresent() && cardToOptional.isPresent()) {
                Card cardFrom = cardFromOptional.get();
                Card cardTo = cardToOptional.get();
                System.out.println("Before creating Transfer: " + transfer);
                Transfer transferResult = new Transfer();
                transferResult.setUuid(UUID.randomUUID().toString());
                transferResult.setDateTime(LocalDateTime.now());
                transferResult.setCardFromNumber(cardFrom.getCardNumber());
                transferResult.setCardToNumber(cardTo.getCardNumber());
                transferResult.setCardFromValidTill(cardFrom.getCardFromValidTill());
                transferResult.setAmount(transfer.getAmount());
                transferResult.setCommission(calculateCommission(BigDecimal.valueOf(transfer.getAmount().getValue())));
                transferResult.setResult("COMPLETED");
                System.out.println("After creating Transfer: " + transferResult);
                transferRepository.addTransfer(transferResult);

                TransferConfirmation confirmation = new TransferConfirmation();
                confirmation.setOperationId(transferResult.getUuid());

                return transferResult;
            } else {
                throw new ErrorInputDataException("Invalid card numbers");
            }
        } catch (ErrorInputDataException | ErrorTransferException e) {
            transferLogger.writeLog("Error transferring money");
            throw e;
        }
    }

    private BigDecimal calculateCommission(BigDecimal amount) {
        return amount.multiply(commission);
    }

    public TransferConfirmation confirmation(TransferConfirmation confirm) {
        Transfer transfer = transferRepository.getTransferByUuid(confirm.getOperationId())
                .orElseThrow(() -> new ErrorTransferException("Uuid not found"));

        if (!confirm.getCode().equals(verifyCode)) {
            transfer.setResult("Error - Invalid verification code");
            transferRepository.addTransfer(transfer);
            transferLogger.writeLog(String.valueOf(transfer));
            throw new ErrorInputDataException("Invalid verification code");
        }

        transferRepository.addTransfer(transfer);
        transferLogger.writeLog(String.valueOf(transfer));

        TransferConfirmation confirmationResult = new TransferConfirmation();
        confirmationResult.setOperationId(transfer.getUuid());

        return confirmationResult;
    }

    private void validateCardExpiry(String cardExpiry) {
        try {
            String pattern = "MM/uu";
            YearMonth dateCheckAd = YearMonth.parse(cardExpiry, DateTimeFormatter.ofPattern(pattern));
            YearMonth currentYearMonth = YearMonth.now();

            if (dateCheckAd.isBefore(currentYearMonth)) {
                throw new ErrorInputDataException("The card is invalid. The validity period has ended");
            }
        } catch (Exception e) {
            throw new ErrorInputDataException("Invalid date format");
        }
    }
}