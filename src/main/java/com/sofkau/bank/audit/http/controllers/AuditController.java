package com.sofkau.bank.audit.http.controllers;

import com.sofkau.bank.audit.http.requests.CreateAuditRequest;
import com.sofkau.bank.audit.http.responses.GetAuditResponse;
import com.sofkau.bank.audit.usecases.AuditUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/audits")
@RequiredArgsConstructor
public class AuditController {
    private final AuditUseCase auditUseCase;

    @GetMapping(value = "/account/{number}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<GetAuditResponse> findAllByAccount(@PathVariable UUID number) {
        return auditUseCase.findAllByAccount(number);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<GetAuditResponse> create(@RequestBody @Valid CreateAuditRequest auditRequest) {
        return auditUseCase.create(auditRequest);
    }
}
