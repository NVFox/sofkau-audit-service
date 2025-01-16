package com.sofkau.bank.audit.usecases;

import com.sofkau.bank.audit.documents.Audit;
import com.sofkau.bank.audit.http.requests.CreateAuditRequest;
import com.sofkau.bank.audit.http.responses.GetAuditResponse;
import com.sofkau.bank.audit.mappers.AuditMapper;
import com.sofkau.bank.audit.services.AuditService;
import com.sofkau.bank.audit.types.OperationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditUseCaseTest {

    @Mock
    private AuditService auditService;

    @Mock
    private AuditMapper auditMapper;

    @InjectMocks
    private AuditUseCase auditUseCase;

    private final UUID mockAccount = UUID.randomUUID();
    private Audit audit;
    private CreateAuditRequest createAuditRequest;
    private GetAuditResponse getAuditResponse;

    @BeforeEach
    void setUp() {
        audit = new Audit();
        createAuditRequest = new CreateAuditRequest(
                UUID.randomUUID(),
                UUID.randomUUID(),
                OperationType.TRANSFER,
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(900),
                BigDecimal.valueOf(500),
                BigDecimal.valueOf(600),
                BigDecimal.valueOf(100)
        );
        getAuditResponse = new GetAuditResponse(
                UUID.randomUUID().toString(),
                createAuditRequest.sourceAccount().toString(),
                createAuditRequest.destinationAccount().toString(),
                createAuditRequest.operationType(),
                createAuditRequest.sourceBalanceBeforeOperation(),
                createAuditRequest.sourceBalanceAfterOperation(),
                createAuditRequest.destinationBalanceBeforeOperation(),
                createAuditRequest.destinationBalanceAfterOperation(),
                createAuditRequest.amount()
        );
    }

    @Test
    void testFindAllByAccount() {
        when(auditService.findAllByAccount(mockAccount))
                .thenReturn(Flux.just(audit));
        when(auditMapper.auditToGetAuditResponse(audit))
                .thenReturn(getAuditResponse);

        Flux<GetAuditResponse> result = auditUseCase.findAllByAccount(mockAccount);

        StepVerifier.create(result)
                .expectNext(getAuditResponse)
                .verifyComplete();
    }

    @Test
    void testFindAllByAccount_Empty() {
        when(auditService.findAllByAccount(mockAccount))
                .thenReturn(Flux.empty());

        Flux<GetAuditResponse> result = auditUseCase.findAllByAccount(mockAccount);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void testCreateAudit() {
        when(auditMapper.createAuditRequestToAudit(createAuditRequest))
                .thenReturn(audit);
        when(auditService.create(audit))
                .thenReturn(Mono.just(audit));
        when(auditMapper.auditToGetAuditResponse(audit))
                .thenReturn(getAuditResponse);

        Mono<GetAuditResponse> result = auditUseCase.create(createAuditRequest);

        StepVerifier.create(result)
                .expectNext(getAuditResponse)
                .verifyComplete();
    }

    @Test
    void testCreateAudit_Error() {
        when(auditMapper.createAuditRequestToAudit(createAuditRequest))
                .thenReturn(audit);
        when(auditService.create(audit))
                .thenReturn(Mono.error(new RuntimeException("Save failed")));

        Mono<GetAuditResponse> result = auditUseCase.create(createAuditRequest);

        StepVerifier.create(result)
                .expectErrorMessage("Save failed")
                .verify();
    }
}