package com.jindero.banking.features.account;


import com.jindero.banking.features.user.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("CHECKINGS")
public class CheckingAccount extends Account implements Chargeable {

  //Konstruktor

  protected CheckingAccount(){
    super();
  }

  protected CheckingAccount(String accountNumber, BigDecimal balance, User user) {
    super(accountNumber, balance, user, AccountType.CHECKING);
  }

  @Override
  public BigDecimal calculateFees() {
    return new BigDecimal("50.00");
  }

  @Override
  public void applyMonthlyFee() {
    BigDecimal fees = calculateFees();
    balance = balance.subtract(calculateFees());
    System.out.println("Deducted " + fees + " from account " + accountNumber);
  }
}
