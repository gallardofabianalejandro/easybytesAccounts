package com.nacionservicios.accounts.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link com.nacionservicios.accounts.entity.Customer}
 */
public record CustomerDto(@NotBlank @Size(max = 100) String name, @NotBlank @Size(max = 100) String email,
                          @NotBlank @Size(max = 20) String mobileNumber, @Valid AccountDto accountDto) implements Serializable {
}