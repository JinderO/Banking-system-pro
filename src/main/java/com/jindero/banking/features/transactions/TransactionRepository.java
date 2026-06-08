package com.jindero.banking.features.transactions;

import com.jindero.banking.features.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

  List<Transaction> findByAccountFrom(Account account);
  List<Transaction> findByAccountTo(Account account);
  List<Transaction> findByAccountFromOrAccountTo(Account accountFrom, Account accountTo);
  List<Transaction> findByDate (LocalDateTime createdAt);


}
