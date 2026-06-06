package com.jindero.banking.features.transactions;

import com.jindero.banking.features.account.Account;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "transactions")
public class Transaction {

  //Fieldy
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne (optional = false)
  @JoinColumn(name = "account_from_id")
  private Account accountFrom;

  @ManyToOne (optional = false)
  @JoinColumn(name = "account_to_id")
  private Account accountTo;

  @Column(nullable = false)
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TransactionStatus status;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TransactionType type;

  private String variableSymbol; //VS
  private String specificSymbol; //SS
  private String note; //poznámka

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  //Konstruktery
  protected Transaction() {
  }

  public Transaction(Account accountFrom, Account accountTo, BigDecimal amount,
                     TransactionType type, String variableSymbol,
                     String specificSymbol, String note) {
    this.accountFrom = accountFrom;
    this.accountTo = accountTo;
    this.amount = amount;
    this.status = TransactionStatus.PENDING;
    this.type = type;
    this.variableSymbol = variableSymbol;
    this.specificSymbol = specificSymbol;
    this.note = note;
  }

  public Transaction(Account accountFrom, Account accountTo, BigDecimal amount,
                     TransactionType type) {
    this(accountFrom, accountTo, amount, type,
            null, null,null);
  }

  //Gettery

  public UUID getId() {
    return id;
  }

  public Account getAccountFrom() {
    return accountFrom;
  }

  public Account getAccountTo() {
    return accountTo;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public TransactionStatus getStatus() {
    return status;
  }

  public TransactionType getType() {
    return type;
  }

  public String getVariableSymbol() {
    return variableSymbol;
  }

  public String getSpecificSymbol() {
    return specificSymbol;
  }

  public String getNote() {
    return note;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt(){
    return updatedAt;
  }

  //Setter


  public void setStatus(TransactionStatus status) {
    this.status = status;
  }

  //Metoda

  @Override
  public String toString(){
    return "Transaction{" +
            "id=" + id +
            ", type=" + type +
            ", status=" + status +
            ", amount=" + amount +
            ", createdAt=" + createdAt +
            "}";
  }
}
