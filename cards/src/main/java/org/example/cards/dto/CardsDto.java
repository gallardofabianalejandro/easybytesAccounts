package org.example.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serializable;

/**
 * DTO for {@link org.example.cards.entity.Cards}
 */
@Schema(
    name = "Card",
    description = "Represents a customer credit/debit card with complete financial information including limits and usage tracking",
    example = """
    {
      "mobileNumber": "1234567890",
      "cardNumber": "4532015112830366",
      "cardType": "CREDIT",
      "totalLimit": 50000,
      "amountUsed": 15000,
      "availableAmount": 35000
    }
    """
)
public record CardsDto(

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
        description = "Unique card identifier (16 digits)",
        example = "4532015112830366",
        required = true,
        minLength = 16,
        maxLength = 16
    )
    @NotEmpty(message = "Card number cannot be null or empty")
    String cardNumber,

    @Schema(
        description = "Type of card",
        example = "CREDIT",
        allowableValues = {"CREDIT", "DEBIT", "PREPAID", "BUSINESS"},
        required = true
    )
    @NotEmpty(message = "Card type cannot be null or empty")
    String cardType,

    @Schema(
        description = "Total credit limit in cents",
        example = "50000",
        minimum = "1000",
        required = true
    )
    @Positive(message = "Total limit must be positive")
    int totalLimit,

    @Schema(
        description = "Amount already used in cents",
        example = "15000",
        minimum = "0",
        required = true
    )
    @PositiveOrZero(message = "Amount used cannot be negative")
    int amountUsed,

    @Schema(
        description = "Available credit amount in cents",
        example = "35000",
        minimum = "0",
        required = true
    )
    @PositiveOrZero(message = "Available amount cannot be negative")
    int availableAmount

) implements Serializable {
}
