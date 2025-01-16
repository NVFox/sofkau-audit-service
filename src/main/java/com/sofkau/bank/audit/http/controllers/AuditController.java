package com.sofkau.bank.audit.http.controllers;

import com.sofkau.bank.audit.http.responses.GetAuditResponse;
import com.sofkau.bank.audit.usecases.AuditUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.UUID;

@RestController
@RequestMapping("/audits")
@RequiredArgsConstructor
public class AuditController {
    private final AuditUseCase auditUseCase;

    @GetMapping("/account/{number}")
    public Flux<GetAuditResponse> findAllByAccount(@PathVariable UUID number) {
        return auditUseCase.findAllByAccount(number);
    }
}
