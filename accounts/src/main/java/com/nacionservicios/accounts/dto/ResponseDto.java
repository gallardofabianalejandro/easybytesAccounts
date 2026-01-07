package com.nacionservicios.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Generic response object containing status information")
public record ResponseDto(
        @Schema(description = "Response status code", example = "200")
        String statusCode,

        @Schema(description = "Response status message", example = "Request processed successfully")
        String statusMsg
) {
}
