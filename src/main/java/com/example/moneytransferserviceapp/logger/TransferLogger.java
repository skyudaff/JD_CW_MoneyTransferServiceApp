package com.example.moneytransferserviceapp.logger;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TransferLogger {
    @Value("${logger.file.path}")
    private String logFilePath;

    public void writeLog(String msg) {
        File file = new File(logFilePath);
        try {
            if (file.createNewFile()) {
                System.out.println("File is created");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            writer.write(msg + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}