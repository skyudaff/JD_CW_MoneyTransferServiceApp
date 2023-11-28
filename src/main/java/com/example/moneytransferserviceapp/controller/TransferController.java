package com.example.moneytransferserviceapp.controller;

import com.example.moneytransferserviceapp.logger.TransferLogger;
import com.example.moneytransferserviceapp.model.Transfer;
import com.example.moneytransferserviceapp.model.TransferConfirmation;
import com.example.moneytransferserviceapp.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class TransferController {
    private final TransferService transferService;
    private final TransferLogger transferLogger;


    @PostMapping("transfer")
    public ResponseEntity<?> getResponse(@RequestBody @Validated Transfer transfer) {
        transferLogger.writeLog("Request transfer: " + transfer);
        return ResponseEntity.ok(transferService.operation(transfer));
    }

    @PostMapping("confirmOperation")
    public ResponseEntity<?> confirmOperation(@RequestBody TransferConfirmation confirm) {
        transferLogger.writeLog("Request confirm: " + confirm);
        return ResponseEntity.ok(transferService.confirmation(confirm));
    }
}
