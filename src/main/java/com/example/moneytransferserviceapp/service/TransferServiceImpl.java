package com.example.moneytransferserviceapp.service;

import com.example.moneytransferserviceapp.dto.Amount;
import com.example.moneytransferserviceapp.dto.Card;
import com.example.moneytransferserviceapp.dto.TransferConfirmation;
import com.example.moneytransferserviceapp.dto.TransferOperation;
import com.example.moneytransferserviceapp.exception.ErrorInputDataException;
import com.example.moneytransferserviceapp.exception.ErrorTransferDataException;
import com.example.moneytransferserviceapp.logger.TransferLogger;
import com.example.moneytransferserviceapp.repository.TransferRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    @Value("${verify.code}")
    private String verifyCode;

    @Value("${transfer.commission}")
    private BigDecimal commission;

    private final TransferRepositoryImpl transferRepository;
    private final TransferLogger transferLogger;

    @Override
    public TransferOperation operation(TransferOperation transfer) {
        try {
            Card cardFrom = getCardByNumber(transfer.getCardFromNumber());
            Card cardTo = getCardByNumber(transfer.getCardToNumber());

            TransferOperation transferResult = transferLogic(cardFrom, cardTo, transfer.getAmount());

            TransferConfirmation confirmation = new TransferConfirmation();
            confirmation.setOperationId(transferResult.getOperationId());

            return transferResult;
        } catch (ErrorInputDataException | ErrorTransferDataException e) {
            transferLogger.writeLog("Error transferring money");
            throw e;
        }
    }

    @Override
    public TransferConfirmation confirmation(TransferConfirmation confirm) {
        String uuid = confirm.getOperationId();
        String code = confirm.getCode();

        TransferOperation transfer = transferRepository.getTransferByUuid(uuid)
                .orElseThrow(() -> new ErrorTransferDataException("Uuid not found"));


        if (!code.equals(verifyCode)) {
            transfer.setResult("Error - Invalid verification code");
            transferRepository.addTransfer(transfer);
            transferLogger.writeLog(String.valueOf(transfer));
            throw new ErrorInputDataException("Invalid verification code");
        }

        transferRepository.addTransfer(transfer);
        transferLogger.writeLog(String.valueOf(transfer));

        TransferConfirmation confirmationResult = new TransferConfirmation();
        confirmationResult.setOperationId(transfer.getOperationId());

        return confirmationResult;
    }

    public Card getCardByNumber(String cardNumber) {
        return transferRepository.getCardByNumber(cardNumber)
                .orElseThrow(() -> new ErrorInputDataException("Invalid card number"));
    }

    private TransferOperation transferLogic(Card cardFrom, Card cardTo, Amount amount) {
        validateCardExpiry(cardFrom.getCardFromValidTill());

        TransferOperation transferResult = new TransferOperation();
        transferResult.setOperationId(UUID.randomUUID().toString());
        transferResult.setDateTime(LocalDateTime.now());
        transferResult.setCardFromNumber(cardFrom.getCardNumber());
        transferResult.setCardToNumber(cardTo.getCardNumber());
        transferResult.setCardFromValidTill(cardFrom.getCardFromValidTill());
        transferResult.setAmount(amount);
        transferResult.setCommission(calculateCommission(BigDecimal.valueOf(amount.getValue())));
        transferResult.setResult("COMPLETED");

        transferRepository.addTransfer(transferResult);

        return transferResult;
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

    private BigDecimal calculateCommission(BigDecimal amount) {
        return amount.multiply(commission);
    }
}