package com.example.moneytransferserviceapp.controller;

import com.example.moneytransferserviceapp.dto.TransferConfirmation;
import com.example.moneytransferserviceapp.dto.TransferOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
public interface TransferController {
    @PostMapping("transfer")
    ResponseEntity<TransferOperation> getResponse(TransferOperation transfer);

    @PostMapping("confirmOperation")
    ResponseEntity<TransferConfirmation> confirmOperation(TransferConfirmation confirm);
}
