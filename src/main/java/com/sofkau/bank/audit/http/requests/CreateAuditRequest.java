package com.sofkau.bank.audit.http.requests;

import com.sofkau.bank.audit.types.OperationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateAuditRequest(
        UUID sourceAccount,
        UUID destinationAccount,
        @NotNull OperationType operationType,
        @PositiveOrZero BigDecimal sourceBalanceBeforeOperation,
        @PositiveOrZero BigDecimal sourceBalanceAfterOperation,
        @PositiveOrZero BigDecimal destinationBalanceBeforeOperation,
        @PositiveOrZero BigDecimal destinationBalanceAfterOperation,
        @NotNull @PositiveOrZero BigDecimal amount
) {
}
