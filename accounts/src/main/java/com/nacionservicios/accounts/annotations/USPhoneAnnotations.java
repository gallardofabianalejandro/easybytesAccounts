package com.nacionservicios.accounts.annotations;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Ejemplo de cómo sobrescribir las anotaciones genéricas del starter
 * para adaptarse a formatos específicos de un país (Estados Unidos).
 *
 * Estas anotaciones tienen prioridad sobre las genéricas del starter.
 */
public class USPhoneAnnotations {

    /**
     * Formato de teléfono específico para Estados Unidos
     * Sobrescribe @ApiAnnotations.PhoneNumberParam
     */
    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    @Parameter(description = "US phone number (XXX-XXX-XXXX)",
               example = "555-123-4567",
               required = true)
    public @interface USPhoneNumberParam {}

    /**
     * Formato de móvil específico para Estados Unidos
     * Sobrescribe @ApiAnnotations.MobileNumberParam
     */
    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    @Parameter(description = "US mobile number (XXX-XXX-XXXX)",
               example = "555-987-6543",
               required = true)
    public @interface USMobileNumberParam {}

    /**
     * Respuestas API específicas para el contexto de Estados Unidos
     * Podrían incluir códigos específicos del negocio
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Resource not found"),
        @ApiResponse(responseCode = "451", description = "Unavailable for legal reasons (US compliance)")
    })
    public @interface USStandardApiResponses {}
}
