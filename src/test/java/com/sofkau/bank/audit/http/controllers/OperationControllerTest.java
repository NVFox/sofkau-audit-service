package com.sofkau.bank.audit.http.controllers;

import com.sofkau.bank.audit.http.requests.DepositRequest;
import com.sofkau.bank.audit.http.requests.TransferRequest;
import com.sofkau.bank.audit.http.requests.WithdrawalRequest;
import com.sofkau.bank.audit.http.responses.GetTransactionResponse;
import com.sofkau.bank.audit.repositories.AuditRepository;
import com.sofkau.bank.audit.types.OperationType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OperationControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private WebClient webClient;

    @Autowired
    private AuditRepository auditRepository;

    private final String emailDeposit = "deposit@mail.com";

    private final String emailWithdrawal = "withdrawal@mail.com";

    private final String emailSender = "transfer_s@mail.com";

    private final String emailReceiver = "transfer_r@mail.com";

    private final String basePassword = "p4sSw0rD";

    @AfterEach
    void tearDown() {
        auditRepository.deleteAll()
                .subscribe();
    }

    @Test
    void testDeposit() {
        String token = createUser(emailDeposit).block();
        String account = createAccount(token, BigDecimal.ZERO).block();

        UUID accountNumber = UUID.fromString(account);
        DepositRequest depositRequest = new DepositRequest(BigDecimal.valueOf(1000));

        webTestClient.post()
                .uri("/operations/deposit/account/{number}", accountNumber)
                .header(HttpHeaders.AUTHORIZATION, token)
                .bodyValue(depositRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(GetTransactionResponse.class)
                .value(response -> {
                    assert response.action().equals(OperationType.DEPOSIT);
                    assert response.amount().equals(depositRequest.amount());
                });
    }

    @Test
    void testWithdrawal() {
        String token = createUser(emailWithdrawal).block();
        String account = createAccount(token, BigDecimal.valueOf(100000)).block();

        UUID accountNumber = UUID.fromString(account);
        WithdrawalRequest withdrawalRequest = new WithdrawalRequest(BigDecimal.valueOf(1000));

        webTestClient.post()
                .uri("/operations/withdrawal/account/{number}", accountNumber)
                .header(HttpHeaders.AUTHORIZATION, token)
                .bodyValue(withdrawalRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(GetTransactionResponse.class)
                .value(response -> {
                    assert response.action().equals(OperationType.WITHDRAWAL);
                    assert response.amount().equals(withdrawalRequest.amount());
                });
    }

    @Test
    void testTransfer() {
        String senderToken = createUser(emailSender).block();
        String senderAccount = createAccount(senderToken, BigDecimal.valueOf(100000)).block();

        String receiverToken = createUser(emailReceiver).block();
        String receiverAccount = createAccount(receiverToken, BigDecimal.ZERO).block();

        TransferRequest transferRequest = new TransferRequest(BigDecimal.valueOf(10000), UUID
                .fromString(receiverAccount));

        webTestClient.post()
                .uri("/operations/transfer/account/{number}", senderAccount)
                .header(HttpHeaders.AUTHORIZATION, senderToken)
                .bodyValue(transferRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(GetTransactionResponse.class)
                .value(response -> {
                    assert response.action().equals(OperationType.TRANSFER);
                    assert response.amount().equals(transferRequest.amount());
                });
    }

    private Mono<String> createUser(String email) {
        Map<String, Object> userRequest = Map.of(
                "email", email,
                "firstName", "John",
                "lastName", "Doe",
                "username", "base",
                "password", basePassword
        );

        return webClient.post()
                .uri("/auth/signup")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(userRequest)
                .exchangeToMono(response -> response
                        .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>(){})
                        .map(user -> "Bearer " + user.get("token").toString()));
    }

    private Mono<String> createAccount(String token, BigDecimal amount) {
        Map<String, Object> accountRequest = Map.of(
                "type", "SAVINGS",
                "openingBalance", amount
        );

        return webClient.post()
                .uri("/accounts")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, token)
                .bodyValue(accountRequest)
                .exchangeToMono(response -> response
                        .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>(){})
                        .map(account -> account.get("number").toString()));

    }
}