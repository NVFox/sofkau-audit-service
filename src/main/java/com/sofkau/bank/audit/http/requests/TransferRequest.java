package com.sofkau.bank.audit.http.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequest(
        @NotNull @PositiveOrZero BigDecimal amount,
        @NotNull  UUID destinationAccount
) {
}
