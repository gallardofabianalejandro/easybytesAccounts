package com.nacionservicios.accounts.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link com.nacionservicios.accounts.entity.Customer}
 */
public record CustomerDto(@NotNull @Size(max = 100) String name, @NotNull @Size(max = 100) String email,
                          @NotNull @Size(max = 20) String mobileNumber) implements Serializable {
}