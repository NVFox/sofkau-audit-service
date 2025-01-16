package com.sofkau.bank.audit.services;

import com.sofkau.bank.audit.documents.Audit;
import com.sofkau.bank.audit.repositories.AuditRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditServiceTest {
    @Mock
    private AuditRepository auditRepository;

    @InjectMocks
    private AuditService auditService;

    private final UUID mockAccount = UUID.randomUUID();

    @Test
    void testFindAllBySourceOrDestinationAccount() {
        Audit audit1 = new Audit();
        Audit audit2 = new Audit();

        when(auditRepository.findAllByAccount(mockAccount.toString()))
                .thenReturn(Flux.just(audit1, audit2));

        Flux<Audit> result = auditService.findAllByAccount(mockAccount);

        StepVerifier.create(result)
                .expectNext(audit1)
                .expectNext(audit2)
                .verifyComplete();
    }

    @Test
    void testFindAllBySourceOrDestinationAccount_Empty() {
        when(auditRepository.findAllByAccount(mockAccount.toString()))
                .thenReturn(Flux.empty());

        Flux<Audit> result = auditService.findAllByAccount(mockAccount);

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void testSaveAudit() {
        Audit audit = new Audit();

        when(auditRepository.save(any(Audit.class)))
                .thenReturn(Mono.just(audit));

        Mono<Audit> result = auditService.create(audit);

        StepVerifier.create(result)
                .expectNext(audit)
                .verifyComplete();
    }

    @Test
    void testSaveAudit_Error() {
        Audit audit = new Audit();

        when(auditRepository.save(any(Audit.class)))
                .thenReturn(Mono.error(new RuntimeException("Save failed")));

        Mono<Audit> result = auditService.create(audit);

        StepVerifier.create(result)
                .expectErrorMessage("Save failed")
                .verify();
    }
}
