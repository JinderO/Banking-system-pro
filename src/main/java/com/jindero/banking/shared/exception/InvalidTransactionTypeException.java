package com.jindero.banking.shared.exception;

public class InvalidTransactionTypeException extends RuntimeException {
  public InvalidTransactionTypeException(String invalidType) {
    super("Unknown transaction type: " + invalidType);
  }
}
