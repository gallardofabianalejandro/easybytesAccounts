package com.nacionservicios.accounts.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponseDto(
        String apiPath, HttpStatus errorCode, String ErrorMessage, LocalDateTime errorTime
        
        ) {
}
