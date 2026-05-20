package com.jindero.banking.features.account;


import com.jindero.banking.features.account.dto.CreateAccountRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {


  private AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  // GET /api/accounts - všechny účty
  @GetMapping
  public List<Account> getAllAccounts(){
    return accountService.getAllAccounts();
  }

  // GET /api/accounts/{id} - jeden účet
  @GetMapping("/{id}")
  public ResponseEntity<Account> getAccountById(@PathVariable Long id){
    Optional<Account> account = accountService.getAccountById(id);
    if (account.isPresent()){
      return ResponseEntity.ok(account.get());
    } else {
      return ResponseEntity.notFound().build();  // ← 404 místo null
    }
  }

  // POST /api/accounts.... - vytvořit účet
  @PostMapping
  public ResponseEntity<Account> createAccount(@Valid @RequestBody CreateAccountRequest request){
    Account account = accountService.createAccount(
            request.getUserId(),
            request.getAccountType(),
            request.getAccountNumber(),
            request.getInitialBalance()
    );
    return ResponseEntity.status(HttpStatus.CREATED).body(account);

  }

  // POST /api/accounts/1/deposit?amount=500
  @PostMapping("/{id}/deposit")
  public Account deposit(@PathVariable Long id, @RequestParam BigDecimal amount){
    return accountService.deposit(id, amount);
  }

  // POST /api/accounts/1/deposit?amount=200
  @PostMapping("/{id}/withdraw")
  public Account withdraw(@PathVariable Long id, @RequestParam BigDecimal amount){
    return accountService.withdraw(id, amount);
  }

}
