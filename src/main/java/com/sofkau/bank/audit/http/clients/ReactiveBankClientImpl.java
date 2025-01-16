package com.sofkau.bank.audit.http.clients;

import com.sofkau.bank.audit.http.requests.DepositRequest;
import com.sofkau.bank.audit.http.requests.TransferRequest;
import com.sofkau.bank.audit.http.requests.WithdrawalRequest;
import com.sofkau.bank.audit.http.responses.GetTransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReactiveBankClientImpl implements ReactiveBankClient {
    private final WebClient webClient;

    @Override
    public Mono<GetTransactionResponse> deposit(String token, UUID number, DepositRequest depositRequest) {
        return webClient.post()
                .uri("/operations/deposit/account/{number}", number)
                .header(HttpHeaders.AUTHORIZATION, token)
                .bodyValue(depositRequest, new ParameterizedTypeReference<>(){})
                .exchangeToMono(response -> response.statusCode().is2xxSuccessful()
                        ? response.bodyToMono(GetTransactionResponse.class)
                        : response.createError());
    }

    @Override
    public Mono<GetTransactionResponse> withdrawal(String token, UUID number, WithdrawalRequest withdrawalRequest) {
        return webClient.post()
                .uri("/operations/withdrawal/account/{number}", number)
                .header(HttpHeaders.AUTHORIZATION, token)
                .bodyValue(withdrawalRequest, new ParameterizedTypeReference<>(){})
                .exchangeToMono(response -> response.statusCode().is2xxSuccessful()
                        ? response.bodyToMono(GetTransactionResponse.class)
                        : response.createError());
    }

    @Override
    public Mono<GetTransactionResponse> transfer(String token, UUID number, TransferRequest transferRequest) {
        return webClient.post()
                .uri("/operations/transfer/account/{number}", number)
                .header(HttpHeaders.AUTHORIZATION, token)
                .bodyValue(transferRequest, new ParameterizedTypeReference<>(){})
                .exchangeToMono(response -> response.statusCode().is2xxSuccessful()
                        ? response.bodyToMono(GetTransactionResponse.class)
                        : response.createError());
    }
}
