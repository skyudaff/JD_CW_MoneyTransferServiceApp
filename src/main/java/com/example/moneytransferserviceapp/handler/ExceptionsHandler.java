package com.example.moneytransferserviceapp.handler;

import com.example.moneytransferserviceapp.exception.ErrorInputDataException;
import com.example.moneytransferserviceapp.exception.ErrorResponse;
import com.example.moneytransferserviceapp.exception.ErrorTransferDataException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@RestControllerAdvice
public class ExceptionsHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(ErrorInputDataException.class)
    public ResponseEntity<ErrorResponse> handleInputDataError(ErrorInputDataException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage(), UUID.randomUUID().toString()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ErrorTransferDataException.class)
    public ResponseEntity<ErrorResponse> handleTransferError(ErrorTransferDataException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage(), UUID.randomUUID().toString()));
    }
}
