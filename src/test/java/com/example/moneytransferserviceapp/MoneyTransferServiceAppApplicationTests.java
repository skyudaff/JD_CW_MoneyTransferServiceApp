package com.example.moneytransferserviceapp;

import com.example.moneytransferserviceapp.dto.TransferConfirmation;
import com.example.moneytransferserviceapp.dto.TransferOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneyTransferServiceAppApplicationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Container
    private final GenericContainer<?> mtsApp = new GenericContainer<>("mtsapp:latest")
            .withExposedPorts(5500);

    @BeforeEach
    void setUp() {
        mtsApp.start();
    }

    @Test
    void contextLoads() {
        ResponseEntity<TransferOperation> entity1 = restTemplate.getForEntity("http://localhost:" + mtsApp.getMappedPort(5500) + "/transfer", TransferOperation.class);
        System.out.println(entity1.getBody());

        ResponseEntity<TransferConfirmation> entity2 = restTemplate.getForEntity("http://localhost:" + mtsApp.getMappedPort(5500) + "/confirmOperation", TransferConfirmation.class);
        System.out.println(entity2.getBody());
    }
}
