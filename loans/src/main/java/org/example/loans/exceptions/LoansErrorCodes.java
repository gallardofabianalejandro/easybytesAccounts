package org.example.loans.exceptions;

import com.company.exceptionhandling.starter.domain.ErrorCode;
import org.springframework.http.HttpStatus;

public interface LoansErrorCodes {

    // LOAN errors
    ErrorCode LOAN_ALREADY_EXISTS = ErrorCode.of(
            "LOAN_ALREADY_EXISTS",
            "LOAN already exists",
            HttpStatus.CONFLICT
    );
    ErrorCode LOAN_NOT_FOUND = ErrorCode.of(
            "LOAN_NOT_FOUND",
            "LOAN not found",
            HttpStatus.NOT_FOUND
    );

    // Account errors
    ErrorCode ACCOUNT_NOT_FOUND = ErrorCode.of(
            "ACCOUNT_NOT_FOUND",
            "Account not found",
            HttpStatus.NOT_FOUND
    );
}
