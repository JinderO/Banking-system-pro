package com.jindero.banking.features.transactions;

import com.jindero.banking.features.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

  //Najít podle odesílatele
 // List<Transaction> findByAccountFrom(Account account);

  //Najít podle příjemce
 // List<Transaction> findByAccountTo(Account account);

  //Najít pokud je účet odesílatelem nebo příjemcem
  List<Transaction> findByAccountFromOrAccountTo(Account accountFrom, Account accountTo);

  //Vyhledat pokud je účet odesílatek nebo přijemce + konkrétní datum hledání
  @Query("SELECT t FROM Transaction t WHERE " +
          "(t.accountFrom = :account OR t.accountTo = :account) " +
          "AND t.createdAt BETWEEN :start AND :end")
  List<Transaction> findHistory(
          @Param("account") Account account,
          @Param("start") LocalDateTime start,
          @Param("end") LocalDateTime end
  );
}
