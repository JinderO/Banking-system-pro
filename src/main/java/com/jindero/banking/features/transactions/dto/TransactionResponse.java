package com.jindero.banking.features.transactions.dto;

import com.jindero.banking.features.transactions.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponse(
        UUID transactionId,
        String fromAccountNumber,
        String toAccountNumber,
        String recipientFullName,
        BigDecimal amount,
        TransactionStatus status,
        LocalDateTime createdAt
) {}