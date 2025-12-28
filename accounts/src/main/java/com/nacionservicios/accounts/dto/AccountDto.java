package com.nacionservicios.accounts.dto;

import com.nacionservicios.accounts.entity.Account;
import jakarta.validation.constraints.*;

import java.io.Serializable;

/**
 * DTO for {@link Account}
 */
public record AccountDto(@NotBlank(message = "Account number is required") @Pattern(regexp = "^\\d{10}$" , message = "Account number is numeric and 10 digits") Long accountNumber,
                         @NotBlank(message = "Account type is required") @Size(max = 100) String accountType,
                         @NotBlank(message = "Branch address is required")      @Size(max = 200) String branchAddress) implements Serializable {
}