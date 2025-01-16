package com.sofkau.bank.audit.usecases;

import com.sofkau.bank.audit.http.clients.ReactiveBankClient;
import com.sofkau.bank.audit.http.requests.DepositRequest;
import com.sofkau.bank.audit.http.requests.TransferRequest;
import com.sofkau.bank.audit.http.requests.WithdrawalRequest;
import com.sofkau.bank.audit.http.responses.GetTransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OperationUseCase {
    private final ReactiveBankClient reactiveBankClient;

    public Mono<GetTransactionResponse> deposit(String token, UUID destinationNumber, DepositRequest depositRequest) {
        return reactiveBankClient.deposit(token, destinationNumber, depositRequest);
    }

    public Mono<GetTransactionResponse> withdrawal(String token, UUID sourceNumber, WithdrawalRequest withdrawalRequest) {
        return reactiveBankClient.withdrawal(token, sourceNumber, withdrawalRequest);
    }

    public Mono<GetTransactionResponse> transfer(String email, UUID sourceNumber, TransferRequest transferRequest) {
        return reactiveBankClient.transfer(email, sourceNumber, transferRequest);
    }
}
