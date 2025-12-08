package com.nacionservicios.accounts.starter.exceptionhandling.handler;

import com.nacionservicios.accounts.starter.exceptionhandling.domain.BaseDomainException;
import com.nacionservicios.accounts.starter.exceptionhandling.domain.ValidationException;
import com.nacionservicios.accounts.starter.exceptionhandling.properties.ExceptionHandlingProperties;
import io.micrometer.tracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Map;

import static net.logstash.logback.argument.StructuredArguments.kv;

/**
 * Modern global exception handler with observability and ProblemDetail.
 */
@ControllerAdvice
public class StarterGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(StarterGlobalExceptionHandler.class);

    private final ExceptionHandlingProperties properties;
    private final Tracer tracer;

    public StarterGlobalExceptionHandler(ExceptionHandlingProperties properties, Tracer tracer) {
        this.properties = properties;
        this.tracer = tracer;
    }

    @ExceptionHandler(BaseDomainException.class)
    public ProblemDetail handleDomainException(BaseDomainException ex, WebRequest request) {
        // ✅ CORRECTED: Save original MDC to prevent memory leaks
        Map<String, String> originalMdc = MDC.getCopyOfContextMap();

        try {
            // ✅ CORRECTED: Add only our specific fields
            MDC.put("error.code", ex.getErrorCode());
            MDC.put("error.http_status", String.valueOf(ex.getHttpStatus().value()));
            MDC.put("error.category", getErrorCategory(ex));

            String traceId = getTraceId();
            String spanId = getSpanId();

            // Structured Logging (enterprise-ready)
            logger.error("Domain exception occurred",
                kv("errorCode", ex.getErrorCode()),
                kv("httpStatus", ex.getHttpStatus().value()),
                kv("traceId", traceId),
                kv("spanId", spanId),
                kv("details", ex.getDetails()),
                kv("requestUri", request.getDescription(false)),
                ex
            );

            ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                ex.getHttpStatus(),
                ex.getMessage()
            );

            // ✅ CRÍTICO: RFC-7807 Type URI
            URI typeUri = URI.create(properties.buildErrorTypeUri(ex.getErrorCode()));
            problem.setType(typeUri);

            problem.setProperty("timestamp", OffsetDateTime.now().toString());
            problem.setProperty("traceId", traceId);
            problem.setProperty("spanId", spanId);
            problem.setProperty("errorCode", ex.getErrorCode());

            ex.getDetails().forEach(problem::setProperty);

            if (properties.includeStackTrace()) {
                problem.setProperty("stackTrace", ex.getStackTrace());
            }

            return problem;

        } finally {
            // ✅ CORRECTED: Restore original MDC (no MDC.clear())
            if (originalMdc != null) {
                MDC.setContextMap(originalMdc);
            } else {
                // Clean only our specific fields if no original MDC existed
                MDC.remove("error.code");
                MDC.remove("error.http_status");
                MDC.remove("error.category");
            }
        }
    }

    @ExceptionHandler(ValidationException.class)
    public ProblemDetail handleValidationException(ValidationException ex, WebRequest request) {
        Map<String, String> originalMdc = MDC.getCopyOfContextMap();

        try {
            MDC.put("error.code", ex.getErrorCode());
            MDC.put("error.http_status", "422");
            MDC.put("error.category", "VALIDATION");
            MDC.put("validation.fieldCount", String.valueOf(ex.getFieldErrors().size()));
            MDC.put("validation.globalCount", String.valueOf(ex.getGlobalErrors().size()));

            String traceId = getTraceId();

            logger.warn("Validation exception occurred",
                kv("errorCode", ex.getErrorCode()),
                kv("traceId", traceId),
                kv("fieldErrors", ex.getFieldErrors()),
                kv("globalErrors", ex.getGlobalErrors()),
                kv("requestUri", request.getDescription(false)),
                ex
            );

            ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNPROCESSABLE_ENTITY,
                ex.getMessage()
            );

            // ✅ RFC-7807 Type URI
            URI typeUri = URI.create(properties.buildErrorTypeUri(ex.getErrorCode()));
            problem.setType(typeUri);

            problem.setTitle("Validation Failed");
            problem.setProperty("timestamp", OffsetDateTime.now().toString());
            problem.setProperty("traceId", traceId);
            problem.setProperty("errorCode", ex.getErrorCode());
            problem.setProperty("fieldErrors", ex.getFieldErrors());
            problem.setProperty("globalErrors", ex.getGlobalErrors());

            return problem;

        } finally {
            if (originalMdc != null) {
                MDC.setContextMap(originalMdc);
            } else {
                MDC.remove("error.code");
                MDC.remove("error.http_status");
                MDC.remove("error.category");
                MDC.remove("validation.fieldCount");
                MDC.remove("validation.globalCount");
            }
        }
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex, WebRequest request) {
        Map<String, String> originalMdc = MDC.getCopyOfContextMap();

        try {
            MDC.put("error.http_status", "500");
            MDC.put("error.category", "GENERIC");

            String traceId = getTraceId();

            logger.error("Unexpected error occurred",
                kv("exceptionType", ex.getClass().getSimpleName()),
                kv("traceId", traceId),
                kv("requestUri", request.getDescription(false)),
                ex
            );

            ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred"
            );

            // ✅ RFC-7807 Type URI for generic errors
            URI typeUri = URI.create(properties.buildErrorTypeUri("INTERNAL_SERVER_ERROR"));
            problem.setType(typeUri);

            problem.setProperty("timestamp", OffsetDateTime.now().toString());
            problem.setProperty("traceId", traceId);
            problem.setProperty("exceptionType", ex.getClass().getSimpleName());

            if (properties.includeStackTrace()) {
                problem.setProperty("stackTrace", ex.getStackTrace());
            }

            return problem;

        } finally {
            if (originalMdc != null) {
                MDC.setContextMap(originalMdc);
            } else {
                MDC.remove("error.http_status");
                MDC.remove("error.category");
            }
        }
    }

    private String getTraceId() {
        return tracer.currentSpan() != null ?
            tracer.currentSpan().context().traceId() : "unknown";
    }

    private String getSpanId() {
        return tracer.currentSpan() != null ?
            tracer.currentSpan().context().spanId() : "unknown";
    }

    private String getErrorCategory(BaseDomainException ex) {
        if (ex instanceof ValidationException) {
            return "VALIDATION";
        } else {
            return "BUSINESS";
        }
    }
}
