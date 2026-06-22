
package com.jindero.banking.features.transactions;

import com.jindero.banking.features.account.Account;
import com.jindero.banking.features.account.AccountRepository;
import com.jindero.banking.features.account.AccountService;
import com.jindero.banking.features.transactions.dto.CreateTransactionRequest;
import com.jindero.banking.features.transactions.dto.TransactionResponse;
import com.jindero.banking.shared.exception.AccountNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  private final AccountRepository accountRepository;
  private final TransactionRepository transactionRepository;
  private final AccountService accountService;

  //Konstruktor
  public TransactionService(AccountRepository accountRepository, TransactionRepository transactionRepository,
                            AccountService accountService){
    this.accountRepository = accountRepository;
    this.transactionRepository = transactionRepository;
    this.accountService = accountService;
  }

  //Vytvoření transakce
  public TransactionResponse transfer(CreateTransactionRequest request){
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






    //Deposit
    Account deposit = accountService.deposit(request.toAccountId(),request.amount());

    // Transakce
    Transaction transaction = new Transaction(senderAccount, receiverAccount, request.amount(), TransactionType.DEPOSIT);


    return TransactionResponse.from(transaction);
  }

  //Vložení

  //Výběr

  //Najít všechny transakce z mého ůčtu

  //Najít transakce od do

  //Najít transakce od do teď

}
