package com.nacionservicios.accounts.starter.exceptionhandling.config;

import com.nacionservicios.accounts.starter.exceptionhandling.handler.StarterGlobalExceptionHandler;
import com.nacionservicios.accounts.starter.exceptionhandling.properties.ExceptionHandlingProperties;
import io.micrometer.tracing.Tracer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Auto-configuration for the exception handling starter.
 * Provides all necessary beans for modern exception handling.
 */
@AutoConfiguration
@EnableConfigurationProperties(ExceptionHandlingProperties.class)
public class ExceptionHandlingAutoConfiguration {

    /**
     * Provides the modern GlobalExceptionHandler as a Spring bean.
     */
    @Bean
    public StarterGlobalExceptionHandler starterGlobalExceptionHandler(
            ExceptionHandlingProperties properties,
            Tracer tracer) {
        return new StarterGlobalExceptionHandler(properties, tracer);
    }
}
