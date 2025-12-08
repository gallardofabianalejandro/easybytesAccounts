package com.nacionservicios.accounts.starter.exceptionhandling.errors;

import com.nacionservicios.accounts.starter.exceptionhandling.domain.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * Account-specific error codes.
 * Provides type safety and prevents typos.
 */
public interface AccountErrorCodes {

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

    // Account errors
    ErrorCode ACCOUNT_NOT_FOUND = ErrorCode.of(
        "ACCOUNT_NOT_FOUND",
        "Account not found",
        HttpStatus.NOT_FOUND
    );

    ErrorCode INSUFFICIENT_FUNDS = ErrorCode.of(
        "INSUFFICIENT_FUNDS",
        "Insufficient funds",
        HttpStatus.BAD_REQUEST
    );

    // Validation errors
    ErrorCode VALIDATION_ERROR = ErrorCode.of(
        "VALIDATION_ERROR",
        "Validation failed",
        HttpStatus.UNPROCESSABLE_ENTITY
    );

    ErrorCode FIELD_VALIDATION_ERROR = ErrorCode.of(
        "FIELD_VALIDATION_ERROR",
        "Field validation failed",
        HttpStatus.UNPROCESSABLE_ENTITY
    );

    ErrorCode MULTIPLE_FIELD_VALIDATION_ERROR = ErrorCode.of(
        "MULTIPLE_FIELD_VALIDATION_ERROR",
        "Multiple field validation errors",
        HttpStatus.UNPROCESSABLE_ENTITY
    );
}
