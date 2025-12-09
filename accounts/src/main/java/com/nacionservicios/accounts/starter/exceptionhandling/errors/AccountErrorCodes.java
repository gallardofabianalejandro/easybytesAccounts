package com.nacionservicios.accounts.starter.exceptionhandling.errors;

import com.company.exceptionhandling.starter.domain.ErrorCode;
import org.springframework.http.HttpStatus;

public interface AccountErrorCodes {

    // Customer errors
    ErrorCode CUSTOMER_ALREADY_EXISTS = ErrorCode.of(
            "CUSTOMER_ALREADY_EXISTS",
            "Customer already exists",
            HttpStatus.CONFLICT
    );
}
