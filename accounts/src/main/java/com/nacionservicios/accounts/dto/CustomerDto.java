package com.nacionservicios.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link com.nacionservicios.accounts.entity.Customer}
 */
@Schema(description = "Customer Data Transfer Object containing customer information and associated account details")
public record CustomerDto(
        @Schema(description = "Customer's full name", example = "John Doe", maxLength = 100)
        @NotBlank(message = "Name is required") @Size(max = 100) String name,

        @Schema(description = "Customer's email address", example = "john.doe@example.com", maxLength = 100)
        @NotBlank(message = "Email is required") @Size(max = 100) @Email(message = "Invalid email format") String email,

        @Schema(description = "Customer's mobile phone number (10 digits)", example = "1234567890", pattern = "^\\d{10}$", maxLength = 20)
        @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits") @NotBlank(message = "Mobile number is required") @Size(max = 20) String mobileNumber,

        @Schema(description = "Associated account information")
        @Valid AccountDto accountDto) implements Serializable {
}
