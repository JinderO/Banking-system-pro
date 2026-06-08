package com.jindero.banking.features.transactions.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateTransactionRequest(
        @NotNull(message = "Source account ID is required")
        UUID fromAccountId,

        @NotNull(message = "Destination account ID is required")
        UUID toAccountId,

        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        BigDecimal amount,

        String variableSymbol,
        String specificSymbol,
        String note
) {}
