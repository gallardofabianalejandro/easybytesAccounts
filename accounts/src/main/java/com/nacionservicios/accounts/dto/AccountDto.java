package com.nacionservicios.accounts.dto;

import com.nacionservicios.accounts.entity.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.io.Serializable;

/**
 * DTO for {@link Account}
 */
@Schema(description = "Account Data Transfer Object containing account information")
public record AccountDto(
        @Schema(description = "Unique account number (10 digits)", example = "1234567890", pattern = "^\\d{10}$")
        @NotBlank(message = "Account number is required") @Pattern(regexp = "^\\d{10}$" , message = "Account number is numeric and 10 digits") Long accountNumber,

        @Schema(description = "Type of account", example = "Savings", maxLength = 100)
        @NotBlank(message = "Account type is required") @Size(max = 100) String accountType,

        @Schema(description = "Branch address where the account is maintained", example = "123 Main Street, New York, NY 10001", maxLength = 200)
        @NotBlank(message = "Branch address is required") @Size(max = 200) String branchAddress) implements Serializable {
}
