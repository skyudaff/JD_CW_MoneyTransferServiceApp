package com.example.moneytransferserviceapp.service;

import com.example.moneytransferserviceapp.dto.TransferConfirmation;
import com.example.moneytransferserviceapp.dto.TransferOperation;

public interface TransferService {
    TransferOperation operation(TransferOperation transfer);

    TransferConfirmation confirmation(TransferConfirmation confirm);
}
