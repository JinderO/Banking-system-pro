package com.jindero.banking.features.account;


import com.jindero.banking.features.user.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("SAVINGS")
public class SavingsAccount extends Account {

  //Konstruktor

  protected SavingsAccount(){
    super();
  }

  protected SavingsAccount(String accountNumber, BigDecimal balance, User user) {
    super(accountNumber, balance, user, AccountType.SAVINGS);
  }
}
