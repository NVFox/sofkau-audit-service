package com.sofkau.bank.audit.http.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record WithdrawalRequest(
        @NotNull @PositiveOrZero BigDecimal amount
        ) {
}
