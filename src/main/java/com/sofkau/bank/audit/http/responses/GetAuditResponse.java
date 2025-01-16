package com.sofkau.bank.audit.http.responses;

import com.sofkau.bank.audit.types.OperationType;

import java.math.BigDecimal;
import java.util.UUID;

public record GetAuditResponse(
        UUID id,
        UUID sourceAccount,
        UUID destinationAccount,
        OperationType operationType,
        BigDecimal sourceBalanceBeforeOperation,
        BigDecimal sourceBalanceAfterOperation,
        BigDecimal destinationBalanceBeforeOperation,
        BigDecimal destinationBalanceAfterOperation,
        BigDecimal amount
) {
}
