package com.example.moneytransferserviceapp.controller;

import com.example.moneytransferserviceapp.logger.TransferLogger;
import com.example.moneytransferserviceapp.dto.TransferConfirmation;
import com.example.moneytransferserviceapp.dto.TransferOperation;
import com.example.moneytransferserviceapp.service.TransferServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransferControllerImpl implements TransferController {
    private final TransferServiceImpl transferService;
    private final TransferLogger transferLogger;

    @Override
    public ResponseEntity<TransferOperation> getResponse(@RequestBody @Validated TransferOperation transfer) {
        transferLogger.writeLog(String.format("Request transfer: %s", transfer));
        return ResponseEntity.ok(transferService.operation(transfer));
    }

    @Override
    public ResponseEntity<TransferConfirmation> confirmOperation(@RequestBody @Validated TransferConfirmation confirm) {
        transferLogger.writeLog(String.format("Request confirm: %s", confirm));
        return ResponseEntity.ok(transferService.confirmation(confirm));
    }
}