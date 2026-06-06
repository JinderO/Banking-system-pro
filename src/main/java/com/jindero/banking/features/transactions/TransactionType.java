package com.jindero.banking.features.transactions;

import com.jindero.banking.shared.exception.InvalidTransactionTypeException;

public enum TransactionType {
  TRANSFER("TRF", "Bankovní převod"),
  DEPOSIT("DEP", "Vklad hotovost"),
  WITHDRAWAL("WTH", "Výběr z bankomatu");

  private final String code;
  private final String description;

  //Konstruktor
  TransactionType(String code, String description) {
    this.code = code;
    this.description = description;
  }

  //Getters

  public String getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }

  //Metody
  public static TransactionType fromString(String value){
    if (value == null){
      return null;
    }
    for (TransactionType type : TransactionType.values()){
      if (type.name().equalsIgnoreCase(value)|| type.getCode().equalsIgnoreCase(value)){
        return type;
      }
    }
    // Exception při jiném něž přednastaveném typu
    throw new  InvalidTransactionTypeException(value);
  }
}
