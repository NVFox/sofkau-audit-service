package com.sofkau.bank.audit.services;

import com.sofkau.bank.audit.documents.Audit;
import com.sofkau.bank.audit.repositories.AuditRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditService {
    private final AuditRepository auditRepository;

    public Flux<Audit> findAllByAccount(@NonNull UUID account) {
        return auditRepository.findAllByAccount(account);
    }

    public Mono<Audit> create(@NonNull Audit audit) {
        return auditRepository.save(audit);
    }
}
