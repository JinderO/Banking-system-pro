package com.jindero.banking.features.account;


import com.jindero.banking.features.user.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("CHECKINGS")
public class CheckingAccount extends Account implements Chargeable {

  private static final Logger log = LoggerFactory.getLogger(CheckingAccount.class);


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
    balance = balance.subtract(fees);
    log.info("Deducted {} from account {}",fees,accountNumber);
  }
}
