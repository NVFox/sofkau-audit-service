package com.sofkau.bank.audit.http.controllers;

import com.sofkau.bank.audit.documents.Audit;
import com.sofkau.bank.audit.http.requests.CreateAuditRequest;
import com.sofkau.bank.audit.http.responses.GetAuditResponse;
import com.sofkau.bank.audit.repositories.AuditRepository;
import com.sofkau.bank.audit.types.OperationType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuditControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private AuditRepository auditRepository;

    private final UUID sourceNumber = UUID.randomUUID();

    private final UUID destinationNumber = UUID.randomUUID();

    @AfterEach
    void tearDown() {
        auditRepository.deleteAll()
                .subscribe();
    }

    @Test
    void testFindAllByAccount() {
        Audit audit = Audit.builder()
                .sourceAccount(sourceNumber.toString())
                .destinationAccount(destinationNumber.toString())
                .operationType(OperationType.TRANSFER)
                .sourceBalanceBeforeOperation(BigDecimal.valueOf(1000))
                .sourceBalanceAfterOperation(BigDecimal.valueOf(900))
                .destinationBalanceBeforeOperation(BigDecimal.valueOf(500))
                .destinationBalanceAfterOperation(BigDecimal.valueOf(600))
                .amount(BigDecimal.valueOf(100))
                .build();

        auditRepository.save(audit)
                .subscribe();

        webTestClient.get()
                .uri("/audits/account/{number}", sourceNumber)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("text/event-stream;charset=UTF-8")
                .returnResult(String.class)
                .getResponseBody()
                .take(1)
                .as(StepVerifier::create)
                .thenCancel()
                .verify();
    }

    @Test
    void testCreateAudit() {
        CreateAuditRequest createAuditRequest = new CreateAuditRequest(
                sourceNumber,
                destinationNumber,
                OperationType.TRANSFER,
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(900),
                BigDecimal.valueOf(500),
                BigDecimal.valueOf(600),
                BigDecimal.valueOf(100)
        );

        webTestClient.post()
                .uri("/audits")
                .bodyValue(createAuditRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(GetAuditResponse.class);
    }
}