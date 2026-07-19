package com.jindero.banking.features.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(
   @Email
   @NotNull(message = "Email is required")
   String email,

   @NotNull(message = "Password is required")
   String password

) {}
