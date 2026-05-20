package com.jindero.banking.features.account;


import com.jindero.banking.features.user.User;
import com.jindero.banking.shared.exception.InsufficientFundsException;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  protected String accountNumber;

  protected BigDecimal balance;

  @Enumerated(EnumType.STRING)
  @Column(name = "account_type_enum")
  private AccountType accountType;

  //Propojeni Account s User
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  //konstruktor

  protected Account() {
  }

  protected Account(String accountNumber, BigDecimal balance, User user,AccountType accountType) {
    this.accountNumber = accountNumber;
    this.balance = balance;
    this.user = user;
    this.accountType = accountType;
  }


  //Metody
  public BigDecimal calculate

  public void deposit(BigDecimal amount) {
    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
       throw new IllegalArgumentException("Chyba! Zadej částku větší než 0!");
    }
    balance = balance.add(amount);
    System.out.println("Vloženo: " + amount + " Kč");
  }

  public void withdraw(BigDecimal amount) {
    if (amount == null || amount.compareTo(BigDecimal.ZERO) > 0) {
      throw new IllegalArgumentException("Částka pro výběr musí být kladná");
    }
    if (balance.compareTo(amount) >= 0) {
      throw new InsufficientFundsException("Nedostatek peněz na účtě");
    }
    balance = balance.subtract(amount);
    System.out.println("Vybráno " + amount + " Kč");
  }

  //Getter
  public AccountType getAccountType() {
    return accountType;
  }

  public BigDecimal getBalance(){
    return balance;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public Long getId() {
    return id;
  }

  public User getUser(){
    return user;
  }


  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Account account = (Account) o;
    return Objects.equals(id, account.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return "Account{" +
            "id=" + id +
            ", accountNumber='" + accountNumber + '\'' +
            ", balance=" + balance +
            ", user=" + user +
            '}';
  }
}
