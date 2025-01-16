package com.sofkau.bank.audit.services;

import com.sofkau.bank.audit.documents.DepositAudit;
import com.sofkau.bank.audit.repositories.DepositAuditRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepositAuditService {
    private final DepositAuditRepository depositAuditRepository;

    public Flux<DepositAudit> findByDestinationAccount(@NonNull UUID destinationAccount) {
        return depositAuditRepository.findByDestinationAccount(destinationAccount);
    }

    public Mono<DepositAudit> create(@NonNull DepositAudit depositAudit) {
        return depositAuditRepository.save(depositAudit);
    }
}
