package com.sofkau.bank.audit.http.controllers;

import com.sofkau.bank.audit.http.requests.DepositRequest;
import com.sofkau.bank.audit.http.requests.TransferRequest;
import com.sofkau.bank.audit.http.requests.WithdrawalRequest;
import com.sofkau.bank.audit.http.responses.GetTransactionResponse;
import com.sofkau.bank.audit.usecases.OperationUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/operations")
@RequiredArgsConstructor
public class OperationController {
    private final OperationUseCase operationUseCase;

    @PostMapping("/deposit/account/{number}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<GetTransactionResponse> deposit(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                @PathVariable UUID number,
                                                @RequestBody @Valid DepositRequest depositRequest) {
        return operationUseCase.deposit(token, number, depositRequest);
    }

    @PostMapping("/withdrawal/account/{number}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<GetTransactionResponse> withdrawal(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                   @PathVariable UUID number,
                                                   @RequestBody @Valid WithdrawalRequest withdrawalRequest) {
        return operationUseCase.withdrawal(token, number, withdrawalRequest);
    }

    @PostMapping("/transfer/account/{number}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<GetTransactionResponse> transfer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                 @PathVariable UUID number,
                                                 @RequestBody @Valid TransferRequest transferRequest) {
        return operationUseCase.transfer(token, number, transferRequest);
    }
}
