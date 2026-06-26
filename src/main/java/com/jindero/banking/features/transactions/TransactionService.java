
package com.jindero.banking.features.transactions;

import com.jindero.banking.features.account.Account;
import com.jindero.banking.features.account.AccountService;
import com.jindero.banking.features.transactions.dto.CreateTransactionRequest;
import com.jindero.banking.features.transactions.dto.TransactionResponse;
import com.jindero.banking.shared.exception.AccountNotFoundException;
import com.jindero.banking.shared.exception.InsufficientFundsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransactionService {


  private final TransactionRepository transactionRepository;
  private final AccountService accountService;

  //Konstruktor
  public TransactionService( TransactionRepository transactionRepository,
                            AccountService accountService){

    this.transactionRepository = transactionRepository;
    this.accountService = accountService;
  }

  //Poslání peněz
  @Transactional
  public TransactionResponse transfer(CreateTransactionRequest request){

    //Kontrola amount
    validateAmount(request.amount());

    //Načtení účtu odesílatele
    Account senderAccount = accountService.getAccountById(request.fromAccountId())
            .orElseThrow(() -> new AccountNotFoundException("Account with ID " + request.fromAccountId() + " not found"));

    //Načtení účtu příjemce
    Account receiverAccount = accountService.getAccountById(request.toAccountId())
            .orElseThrow(() -> new AccountNotFoundException("Account with ID " + request.toAccountId() + " not found"));

    //Porovnání zda odesílatel a příjemce není jeden účet
    if (senderAccount.equals(receiverAccount)){
      throw new IllegalArgumentException("Sender and receiver accounts must be different.");
    }

    //Kontrola vůči zůstatku
    if (request.amount().compareTo(senderAccount.getBalance())>0){
      throw new InsufficientFundsException("Insufficient funds on the source account.");
    }

    //Odečtení peněz odesílateli
    accountService.withdraw(senderAccount.getId(), request.amount());

    //Připsání peněz příjemci
    accountService.deposit(receiverAccount.getId(),request.amount());

    // Transakce
    Transaction transaction = Transaction.forTransfer(senderAccount, receiverAccount, request.amount());
    Transaction saved = transactionRepository.save(transaction);
    return TransactionResponse.from(saved);
  }

  //Vložení peněz
  @Transactional
  public TransactionResponse deposit(CreateTransactionRequest request) {

    //Kontrola amount
    validateAmount(request.amount());

    //Načtení účtu příjemce
    Account receiverAccount = accountService.getAccountById(request.toAccountId())
            .orElseThrow(() -> new AccountNotFoundException("Account with ID " + request.toAccountId() + " not found"));


    accountService.deposit(receiverAccount.getId(), request.amount());

    // Transakce
    Transaction transaction = Transaction.forDeposit(receiverAccount, request.amount());
    Transaction saved = transactionRepository.save(transaction);
    return TransactionResponse.from(saved);
  }

    //Výběr peněz
    @Transactional
    public TransactionResponse withdrawal(CreateTransactionRequest request) {

      //Kontrola amount
      validateAmount(request.amount());

    //Načtení účtu
      Account senderAccount = accountService.getAccountById(request.fromAccountId())
              .orElseThrow(() -> new AccountNotFoundException("Account with ID " + request.fromAccountId() + " not found"));

      //Kontrola vůči zůstatku
      if (request.amount().compareTo(senderAccount.getBalance())>0){
        throw new InsufficientFundsException("Insufficient funds on the source account.");
      }

      accountService.withdraw(senderAccount.getId(), request.amount());

      // Transakce
      Transaction transaction = Transaction.forWithdrawal(senderAccount, request.amount());
      Transaction saved = transactionRepository.save(transaction);
      return TransactionResponse.from(saved);

    }


  //Najít všechny transakce z mého ůčtu

  //Najít transakce od do

  //Najít transakce od do teď

  // Helper metody
  private void validateAmount(BigDecimal amount){
    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
      throw new IllegalArgumentException("Transaction amount must be greater than zero.");
    }
  }

}
