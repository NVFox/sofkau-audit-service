package com.sofkau.bank.audit.usecases;

import com.sofkau.bank.audit.http.responses.GetAuditResponse;
import com.sofkau.bank.audit.mappers.AuditMapper;
import com.sofkau.bank.audit.services.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

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
}
