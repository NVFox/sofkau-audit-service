package com.sofkau.bank.audit.http.clients;

import com.sofkau.bank.audit.http.requests.DepositRequest;
import com.sofkau.bank.audit.http.requests.TransferRequest;
import com.sofkau.bank.audit.http.requests.WithdrawalRequest;
import com.sofkau.bank.audit.http.responses.GetTransactionResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ReactiveBankClient {
    Mono<GetTransactionResponse> deposit(String token, UUID number, DepositRequest depositRequest);
    Mono<GetTransactionResponse> withdrawal(String token, UUID number, WithdrawalRequest withdrawalRequest);
    Mono<GetTransactionResponse> transfer(String token, UUID number, TransferRequest transferRequest);
}
