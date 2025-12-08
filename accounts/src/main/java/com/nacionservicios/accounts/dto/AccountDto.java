package com.nacionservicios.accounts.dto;

import com.nacionservicios.accounts.entity.Account;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link Account}
 */
public record AccountDto(Long accountNumber, @NotNull @Size(max = 100) String accountType,
                         @NotNull @Size(max = 200) String branchAddress) implements Serializable {
}