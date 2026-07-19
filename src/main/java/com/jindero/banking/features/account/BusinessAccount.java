package com.jindero.banking.features.account;


import com.jindero.banking.features.user.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("BUSINESS")
public class BusinessAccount extends Account implements Chargeable {

  private static final Logger log = LoggerFactory.getLogger(BusinessAccount.class);

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
    log.info("Deducted {} from account {}",fees,accountNumber);
  }
}
