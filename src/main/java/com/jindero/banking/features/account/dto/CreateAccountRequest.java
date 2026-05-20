package com.jindero.banking.features.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class CreateAccountRequest {

  @NotNull(message = "User ID is required")
  private Long userId;

  @NotBlank(message = "Account type is required")
  private String accountType;

  @NotBlank(message = "Account number is required")
  private String accountNumber;

  @NotNull(message = "Initial balance is required")
  @Positive(message = "Initial balance must be positive")
  private BigDecimal initialBalance;

  //Konstruktor

  public CreateAccountRequest() {}

  public CreateAccountRequest(Long userId, String accountType, String accountNumber, BigDecimal initialBalance) {
    this.userId = userId;
    this.accountType = accountType;
    this.accountNumber = accountNumber;
    this.initialBalance = initialBalance;
  }

  //Getters a Setters

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public BigDecimal getInitialBalance() {
    return initialBalance;
  }

  public void setInitialBalance(BigDecimal initialBalance) {
    this.initialBalance = initialBalance;
  }
}
