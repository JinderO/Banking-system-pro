package com.jindero.banking.features.user.dto;

public record AuthResponse(
        String token
) {
    //Statická factory metoda
    public static AuthResponse of(String token) {
        return new AuthResponse(token);
    }
}
