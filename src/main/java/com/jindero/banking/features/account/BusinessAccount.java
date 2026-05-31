package com.jindero.banking.features.account;


import com.jindero.banking.features.user.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("BUSINESS")
public class BusinessAccount extends Account implements Chargeable {

  //Konstruktor

  protected BusinessAccount(){
    super();
  }

  protected BusinessAccount(String accountNumber, BigDecimal balance, User user) {
    super(accountNumber, balance, user, AccountType.BUSINESS);
  }

  @Override
  public BigDecimal calculateFees() {
    return new BigDecimal("200.00");
  }

  @Override
  public void applyMonthlyFee() {
    BigDecimal fees = calculateFees();
    balance = balance.subtract(fees);
    System.out.println("Deducted " + fees + " from account " + accountNumber);
  }
}
