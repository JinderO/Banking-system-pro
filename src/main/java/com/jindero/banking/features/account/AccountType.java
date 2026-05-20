package com.jindero.banking.features.account;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum AccountType {

  SAVINGS(new BigDecimal("0.015"), BigDecimal.ZERO),
  CHECKING(new BigDecimal("0.005"), new BigDecimal("50")),
  BUSINESS(new BigDecimal("0.01"), new BigDecimal("200"));

  private final BigDecimal interestRate;
  private final BigDecimal monthlyFee;

  AccountType(BigDecimal interestRate, BigDecimal monthlyFee){
    this.interestRate = interestRate;
    this.monthlyFee = monthlyFee;
  }

  // Gettery
  public BigDecimal getInterestRate() {
    return interestRate;
  }

  public BigDecimal getMonthlyFee() {
    return monthlyFee;
  }

  //Metody

  public static AccountType fromString(String type) {
    return switch (type.toUpperCase()) {
      case "SAVINGS" -> SAVINGS;
      case "CHECKING" -> CHECKING;
      case "BUSINESS" -> BUSINESS;
      default -> throw new IllegalArgumentException("Invalid: " + type);
    };
  }

  public BigDecimal calculateMonthlyInterest(BigDecimal balance){
    return balance.multiply(interestRate)
            .divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
  }

}
