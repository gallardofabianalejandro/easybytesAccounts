package org.example.cards.errors;

import com.company.exceptionhandling.starter.domain.ErrorCode;
import org.springframework.http.HttpStatus;

public interface CardErrorCodes {

    // Customer errors
    ErrorCode CUSTOMER_ALREADY_EXISTS = ErrorCode.of(
            "CUSTOMER_ALREADY_EXISTS",
            "Customer already exists",
            HttpStatus.CONFLICT
    );
    ErrorCode CUSTOMER_NOT_FOUND = ErrorCode.of(
            "CUSTOMER_NOT_FOUND",
            "Customer not found",
            HttpStatus.NOT_FOUND
    );

    // CARD errors
    ErrorCode CARD_NOT_FOUND = ErrorCode.of(
            "CARD_NOT_FOUND",
            "CARD not found",
            HttpStatus.NOT_FOUND
    );
}
