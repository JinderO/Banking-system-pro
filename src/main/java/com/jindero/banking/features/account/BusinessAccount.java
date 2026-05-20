package com.jindero.banking.features.account;


import com.jindero.banking.features.user.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.hibernate.sql.results.graph.collection.internal.BagInitializer;

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
  public String getAccountType() {
    return "Business Account";
  }


  @Override
  public double calculateFees() {
    double fees = 200.00;
    return fees;
  }

  @Override
  public void applyMonthlyFee() {
    balance -= calculateFees();
    System.out.println("Odečteno " + calculateFees() + " z účtu " + accountNumber);
  }
}
