package com.jindero.banking.shared.exception;

public class TransactionNotFoundException extends RuntimeException {
  public TransactionNotFoundException(String TransactionID) {
    super("Transaction "+TransactionID + "was not found");
  }
}
