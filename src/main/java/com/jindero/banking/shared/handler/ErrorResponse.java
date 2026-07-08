package com.jindero.banking.shared.handler;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponse(
        int status,
        String error,
        String message,
        LocalDateTime timestamp
) {
    //Statická factory metoda
    public static ErrorResponse of(HttpStatus status, Exception ex){
        return new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                LocalDateTime.now()

        );
    }

}
