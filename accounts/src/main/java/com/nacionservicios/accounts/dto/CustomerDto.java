package com.nacionservicios.accounts.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link com.nacionservicios.accounts.entity.Customer}
 */
public record CustomerDto(@NotBlank(message = "Name is required") @Size(max = 100) String name,
                          @NotBlank(message = "Email is required") @Size(max = 100) @Email String email,
                          @Pattern(regexp = "^\\d{10}$") @NotBlank(message = "Mobile number is required") @Size(max = 20) String mobileNumber,
                          @Valid AccountDto accountDto) implements Serializable {
}