package com.sofkau.bank.audit.usecases;

import com.sofkau.bank.audit.http.clients.ReactiveBankClient;
import com.sofkau.bank.audit.http.requests.DepositRequest;
import com.sofkau.bank.audit.http.requests.TransferRequest;
import com.sofkau.bank.audit.http.requests.WithdrawalRequest;
import com.sofkau.bank.audit.http.responses.GetTransactionResponse;
import com.sofkau.bank.audit.exceptions.OperationNotCommitedException;
import com.sofkau.bank.audit.types.OperationType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OperationUseCaseTest {

    @Mock
    private ReactiveBankClient reactiveBankClient;

    @InjectMocks
    private OperationUseCase operationUseCase;

    private final String token = "t0k3n";
    private final UUID accountNumber = UUID.randomUUID();

    @Test
    void testDeposit() {
        DepositRequest depositRequest = new DepositRequest(BigDecimal.valueOf(1000));

        GetTransactionResponse transactionResponse = new GetTransactionResponse(
                accountNumber,
                OperationType.DEPOSIT,
                BigDecimal.valueOf(1000),
                "Transaction description",
                LocalDateTime.now()
        );

        when(reactiveBankClient.deposit(any(String.class), any(UUID.class), any(DepositRequest.class)))
                .thenReturn(Mono.just(transactionResponse));

        Mono<GetTransactionResponse> result = operationUseCase.deposit(token, accountNumber, depositRequest);

        StepVerifier.create(result)
                .expectNext(transactionResponse)
                .verifyComplete();
    }

    @Test
    void testDeposit_Error() {
        DepositRequest depositRequest = new DepositRequest(BigDecimal.valueOf(1000));

        when(reactiveBankClient.deposit(any(String.class), any(UUID.class), any(DepositRequest.class)))
                .thenReturn(Mono.error(new OperationNotCommitedException("Deposit operation could not be committed")));

        Mono<GetTransactionResponse> result = operationUseCase.deposit(token, accountNumber, depositRequest);

        StepVerifier.create(result)
                .expectErrorMessage("Deposit operation could not be committed")
                .verify();
    }

    @Test
    void testWithdrawal() {
        WithdrawalRequest withdrawalRequest = new WithdrawalRequest(BigDecimal.valueOf(500));

        GetTransactionResponse transactionResponse = new GetTransactionResponse(
                accountNumber,
                OperationType.WITHDRAWAL,
                BigDecimal.valueOf(500),
                "Transaction description",
                LocalDateTime.now()
        );

        when(reactiveBankClient.withdrawal(any(String.class), any(UUID.class), any(WithdrawalRequest.class)))
                .thenReturn(Mono.just(transactionResponse));

        Mono<GetTransactionResponse> result = operationUseCase.withdrawal(token, accountNumber, withdrawalRequest);

        StepVerifier.create(result)
                .expectNext(transactionResponse)
                .verifyComplete();
    }

    @Test
    void testWithdrawal_Error() {
        WithdrawalRequest withdrawalRequest = new WithdrawalRequest(BigDecimal.valueOf(500));

        when(reactiveBankClient.withdrawal(any(String.class), any(UUID.class), any(WithdrawalRequest.class)))
                .thenReturn(Mono.error(new OperationNotCommitedException("Withdrawal operation could not be committed")));

        Mono<GetTransactionResponse> result = operationUseCase.withdrawal(token, accountNumber, withdrawalRequest);

        StepVerifier.create(result)
                .expectErrorMessage("Withdrawal operation could not be committed")
                .verify();
    }

    @Test
    void testTransfer() {
        TransferRequest transferRequest = new TransferRequest(BigDecimal.valueOf(300), accountNumber);

        GetTransactionResponse transactionResponse = new GetTransactionResponse(
                accountNumber,
                OperationType.TRANSFER,
                BigDecimal.valueOf(300),
                "Transaction description",
                LocalDateTime.now()
        );

        when(reactiveBankClient.transfer(any(String.class), any(UUID.class), any(TransferRequest.class)))
                .thenReturn(Mono.just(transactionResponse));

        Mono<GetTransactionResponse> result = operationUseCase.transfer(token, accountNumber, transferRequest);

        StepVerifier.create(result)
                .expectNext(transactionResponse)
                .verifyComplete();
    }

    @Test
    void testTransfer_Error() {
        TransferRequest transferRequest = new TransferRequest(BigDecimal.valueOf(300), accountNumber);

        when(reactiveBankClient.transfer(any(String.class), any(UUID.class), any(TransferRequest.class)))
                .thenReturn(Mono.error(new OperationNotCommitedException("Transfer operation could not be committed")));

        Mono<GetTransactionResponse> result = operationUseCase.transfer(token, accountNumber, transferRequest);

        StepVerifier.create(result)
                .expectErrorMessage("Transfer operation could not be committed")
                .verify();
    }
}