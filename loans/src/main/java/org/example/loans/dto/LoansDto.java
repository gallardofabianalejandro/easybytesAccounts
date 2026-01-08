package org.example.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serializable;

/**
 * DTO for {@link org.example.loans.entity.Loans}
 */
@Schema(
    name = "Loan",
    description = "Represents a customer loan with complete financial information",
    example = """
    {
      "mobileNumber": "1234567890",
      "loanNumber": "L20240001",
      "loanType": "PERSONAL",
      "totalLoan": 50000,
      "amountPaid": 15000,
      "outstandingAmount": 35000
    }
    """
)
public record LoansDto(

    @Schema(
        description = "Customer's mobile number (10 digits)",
        example = "1234567890",
        pattern = "^\\d{10}$",
        required = true,
        minLength = 10,
        maxLength = 10
    )
    @NotEmpty(message = "Mobile number cannot be null or empty")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be exactly 10 digits")
    String mobileNumber,

    @Schema(
        description = "Unique loan identifier",
        example = "L20240001",
        required = true,
        minLength = 9,
        maxLength = 9
    )
    @NotEmpty(message = "Loan number cannot be null or empty")
    String loanNumber,

    @Schema(
        description = "Type of loan",
        example = "PERSONAL",
        allowableValues = {"PERSONAL", "HOME", "CAR", "BUSINESS"},
        required = true
    )
    @NotEmpty(message = "Loan type cannot be null or empty")
    String loanType,

    @Schema(
        description = "Total loan amount in cents",
        example = "50000",
        minimum = "1000",
        required = true
    )
    @Positive(message = "Total loan amount must be positive")
    int totalLoan,

    @Schema(
        description = "Amount already paid in cents",
        example = "15000",
        minimum = "0",
        required = true
    )
    @PositiveOrZero(message = "Amount paid cannot be negative")
    int amountPaid,

    @Schema(
        description = "Outstanding amount to be paid in cents",
        example = "35000",
        minimum = "0",
        required = true
    )
    @PositiveOrZero(message = "Outstanding amount cannot be negative")
    int outstandingAmount

) implements Serializable {
}
