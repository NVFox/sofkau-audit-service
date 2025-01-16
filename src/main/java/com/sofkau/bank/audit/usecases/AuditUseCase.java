package com.sofkau.bank.audit.usecases;

import com.sofkau.bank.audit.http.requests.CreateAuditRequest;
import com.sofkau.bank.audit.http.responses.GetAuditResponse;
import com.sofkau.bank.audit.mappers.AuditMapper;
import com.sofkau.bank.audit.services.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditUseCase {
    private final AuditService auditService;
    private final AuditMapper auditMapper;

    public Flux<GetAuditResponse> findAllByAccount(UUID number) {
        return auditService.findAllByAccount(number)
                .map(auditMapper::auditToGetAuditResponse);
    }

    public Mono<GetAuditResponse> create(CreateAuditRequest auditRequest) {
        return auditService.create(auditMapper.createAuditRequestToAudit(auditRequest))
                .map(auditMapper::auditToGetAuditResponse);
    }
}
