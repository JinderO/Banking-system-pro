package com.jindero.banking.features.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateAccountRequest(
        @NotNull(message = "User ID is required")
        UUID userId,

        @NotBlank(message = "Account type is required")
        String accountType,

        @NotBlank(message = "Account number is required")
        String accountNumber,

        @NotNull(message = "Initial balance is required")
        @Positive(message = "Initial balance must be positive")
        BigDecimal initialBalance
) {}