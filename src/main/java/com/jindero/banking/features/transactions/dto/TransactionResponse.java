package com.jindero.banking.features.transactions.dto;

import com.jindero.banking.features.transactions.Transaction;
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
) {
  // Statická factory metoda
  public static TransactionResponse from(Transaction transaction) {
    return new TransactionResponse(
            transaction.getId(),

            // Číslo účtu odesílatele
            transaction.getAccountFrom() != null ? transaction.getAccountFrom().getAccountNumber() : "Bankomat/Vklad",

            // Číslo účtu příjemce
            transaction.getAccountTo() != null ? transaction.getAccountTo().getAccountNumber() : null,

            // Jméno příjemce: Spojení firstName + " " + lastName z entity User
            transaction.getAccountTo() != null && transaction.getAccountTo().getUser() != null
                    ? transaction.getAccountTo().getUser().getFirstName() + " " + transaction.getAccountTo().getUser().getLastName()
                    : null,

            transaction.getAmount(),
            transaction.getStatus(),
            transaction.getCreatedAt()
    );
  }
}