package com.sofkau.bank.audit.services;

import com.sofkau.bank.audit.documents.WithdrawalAudit;
import com.sofkau.bank.audit.repositories.WithdrawalAuditRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WithdrawAuditService {
    private final WithdrawalAuditRepository withdrawalAuditRepository;

    public Flux<WithdrawalAudit> findBySourceAccount(@NonNull UUID sourceAccount) {
        return withdrawalAuditRepository.findBySourceAccount(sourceAccount);
    }

    public Mono<WithdrawalAudit> create(@NonNull WithdrawalAudit withdrawalAudit) {
        return withdrawalAuditRepository.save(withdrawalAudit);
    }
}
