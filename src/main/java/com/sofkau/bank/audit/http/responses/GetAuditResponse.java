package com.sofkau.bank.audit.http.responses;

import com.sofkau.bank.audit.types.OperationType;

import java.math.BigDecimal;

public record GetAuditResponse(
        String id,
        String sourceAccount,
        String destinationAccount,
        OperationType operationType,
        BigDecimal sourceBalanceBeforeOperation,
        BigDecimal sourceBalanceAfterOperation,
        BigDecimal destinationBalanceBeforeOperation,
        BigDecimal destinationBalanceAfterOperation,
        BigDecimal amount
) {
}
