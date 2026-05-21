package com.jindero.banking.features.account;

import java.math.BigDecimal;

public interface Chargeable {

  BigDecimal calculateFees();
  void applyMonthlyFee();
}
