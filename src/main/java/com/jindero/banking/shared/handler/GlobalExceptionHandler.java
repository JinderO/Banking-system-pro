package com.jindero.banking.shared.handler;

import com.jindero.banking.shared.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Metoda pro ošetření AccountNotFoundException
    @ExceptionHandler(AccountNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleAccountNotFoundException (AccountNotFoundException ex){
            ErrorResponse response = ErrorResponse.of(HttpStatus.NOT_FOUND,ex);
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Metoda pro ošetření EmailAlreadyExistException
    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistException (EmailAlreadyExistException ex){
        ErrorResponse response = ErrorResponse.of(HttpStatus.CONFLICT, ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // Metoda pro ošetření InsufficientFundsException
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> InsufficientFundsException (InsufficientFundsException ex){
        ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Metoda pro ošetření InvalidTransactionTypeException
    @ExceptionHandler(InvalidTransactionTypeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTransactionTypeException (InvalidTransactionTypeException ex){
        ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Metoda pro ošetření TransactionNotFoundException
    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTransactionNotFoundException (TransactionNotFoundException ex){
        ErrorResponse response = ErrorResponse.of(HttpStatus.NOT_FOUND,ex);
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Metoda pro ošetření IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException (IllegalArgumentException ex){
        ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST,ex);
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Metoda pro ošetření Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException (Exception ex){
        ErrorResponse response = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR,ex);
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
