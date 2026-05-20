package com.jindero.banking.features.account;


import com.jindero.banking.features.user.User;
import com.jindero.banking.features.user.UserRepository;
import com.jindero.banking.shared.exception.AccountNotFoundException;
import com.jindero.banking.shared.exception.InsufficientFundsException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

  private AccountRepository accountRepository;
  private UserRepository userRepository;

  public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
    this.accountRepository = accountRepository;
    this.userRepository = userRepository;
  }

  //vytvořit account

  public Account createAccount(Long userId,String accountType,
                               String accountNumber, BigDecimal initialBalance){

    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User with ID " + userId + " not found"));

    // Převod String -> enum
    AccountType type = AccountType.fromString(accountType);

    // Switch podle enum
    Account account =  switch (type){
      case SAVINGS -> new SavingsAccount(accountNumber, initialBalance, user);
      case CHECKING -> new CheckingAccount(accountNumber, initialBalance,user);
      case BUSINESS -> new BusinessAccount(accountNumber, initialBalance,user);
    };
    return accountRepository.save(account);
  }

  // Zobrazit všechny učty
  public List<Account> getAllAccounts(){
    return accountRepository.findAll();
  }

  // Najít účet pomocí ID
  public Optional<Account> getAccountById(Long id){
    if (id == null || id <= 0){
      return Optional.empty();
    }
    return accountRepository.findById(id);
  }

  // Vložení peněz
  public Account deposit(Long accountId, BigDecimal amount){
    if (amount.compareTo(BigDecimal.ZERO) <= 0){
      throw new IllegalArgumentException("Deposit amount must be positive");
    }

    Optional<Account> accountOpt = accountRepository.findById(accountId);
    if (accountOpt.isPresent()){
      Account account = accountOpt.get();
      account.deposit(amount);
      return accountRepository.save(account);
    }
    throw new AccountNotFoundException("Account with ID " + accountId + " not found");
  }

  // Výběr peněz
  public Account withdraw(Long accountId,BigDecimal amount){
    if (amount.compareTo(BigDecimal.ZERO) <= 0){
      throw new IllegalArgumentException("Withdrawal amount must be positive");
    }

    Optional<Account> accountOpt = accountRepository.findById(accountId);
    if (accountOpt.isPresent()){
      Account account = accountOpt.get();
      if (account.withdraw(amount)){
        return accountRepository.save(account);
      }
      throw  new InsufficientFundsException("Insufficient funds for withdrawal of " + amount);
    }
    throw new AccountNotFoundException("Account with ID "+ accountId+ " not found");
  }

}
