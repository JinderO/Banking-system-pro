package com.jindero.banking.features.transactions;

import com.jindero.banking.features.transactions.dto.CreateTransactionRequest;
import com.jindero.banking.features.transactions.dto.TransactionResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService){
    this.transactionService = transactionService;
  }

  // POST /api/transactions/transfer
  @PostMapping("/transfer")
  public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody CreateTransactionRequest request){
    TransactionResponse response = transactionService.transfer(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  // POST /api/transactions/deposit
  @PostMapping("/deposit")
  public ResponseEntity<TransactionResponse> deposit(@Valid @RequestBody CreateTransactionRequest request){
    TransactionResponse response = transactionService.deposit(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  // POST /api/transactions/withdrawn
  @PostMapping("/withdrawn")
  public ResponseEntity<TransactionResponse> withdrawn(@Valid @RequestBody CreateTransactionRequest request){
    TransactionResponse response = transactionService.withdrawal(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  //GET /api/transactions/history
  @GetMapping("/history")
  public List<TransactionResponse> getHistory(@RequestParam UUID accountId) {
    return transactionService.getHistoryByAccount(accountId);
  }

  //GET /api/transactions/history/range
  @GetMapping("/history/range")
  public List<TransactionResponse> getHistoryByDateRange(@RequestParam UUID accountId,
                                                         @RequestParam LocalDateTime start,
                                                         @RequestParam LocalDateTime end) {
    return transactionService.getHistoryByDateRange(accountId, start, end);
  }
    //GET /api/transactions/history/from
    @GetMapping("/history/from")
    public List<TransactionResponse> getHistoryFromDateToNow(@RequestParam UUID accountId,
                                                             @RequestParam LocalDateTime start){
      return transactionService.getHistoryFromDateToNow(accountId,start);
    }

}
