package com.sofkau.bank.audit.http.responses;

import com.sofkau.bank.audit.types.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetTransactionResponse(
        UUID id,
        OperationType action,
        BigDecimal amount,
        String description,
        LocalDateTime date
) {
}
