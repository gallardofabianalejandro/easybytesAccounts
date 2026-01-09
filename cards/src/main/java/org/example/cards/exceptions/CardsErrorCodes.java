package org.example.cards.exceptions;

import com.company.exceptionhandling.starter.domain.ErrorCode;
import org.springframework.http.HttpStatus;

public interface CardsErrorCodes {

    // CARD errors
    ErrorCode CARD_ALREADY_EXISTS = ErrorCode.of(
            "CARD_ALREADY_EXISTS",
            "CARD already exists",
            HttpStatus.CONFLICT
    );
    ErrorCode CARD_NOT_FOUND = ErrorCode.of(
            "CARD_NOT_FOUND",
            "CARD not found",
            HttpStatus.NOT_FOUND
    );

    // Account errors
    ErrorCode ACCOUNT_NOT_FOUND = ErrorCode.of(
            "ACCOUNT_NOT_FOUND",
            "Account not found",
            HttpStatus.NOT_FOUND
    );
}
