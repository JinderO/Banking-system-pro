package com.jindero.banking.features.transactions;

public enum TransactionStatus {
  COMPLETED("COMP", "Úspěšně dokončeno"),
  PENDING("PEN", "Čeká na vyřízení"),
  CANCELED("CANC", "Zrušeno"),
  FAILED("FAIL", "Vyskytla se chyba");

  private final String code;
  private final String description;

  //Konstruktor
  TransactionStatus(String code, String description){
    this.code = code;
    this.description = description;
  }

  // Getters
  public String getCode(){
    return code;
  }

  public String getDescription() {
    return description;
  }


}
