package org.example.loans.controllers;

import com.openapi.autoconfigure.openapi.ApiAnnotations;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.example.loans.constants.LoansConstants;
import org.example.loans.dto.LoansDto;
import org.example.loans.dto.ResponseDto;
import org.example.loans.services.ILoansService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.example.loans.constants.LoansConstants.MESSAGE_201;
import static org.example.loans.constants.LoansConstants.STATUS_201;

@RestController
@RequestMapping(path = "/api/loans", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
@Tag(
    name = "Loans Management",
    description = "Complete loan management APIs for creating, retrieving, updating and deleting customer loans. " +
                  "Supports personal, home, car and business loans with comprehensive financial tracking."
)
public class LoanController {

    private final ILoansService loanService;

    @Operation(
        summary = "Create a new customer loan",
        description = "Creates a new loan for an existing customer identified by their mobile number. " +
                     "The system will generate a unique loan number and set up the loan with default terms.",
        operationId = "createLoan"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Loan created successfully",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ResponseDto.class),
            examples = {
                @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Loan Created",
                    summary = "Successful loan creation",
                    value = """
                    {
                      "statusCode": "201",
                      "statusMsg": "Loan created successfully"
                    }
                    """
                )
            }
        )
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(
        @ApiAnnotations.MobileNumberParam
        @RequestParam
        @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be exactly 10 digits")
        String mobileNumber
    ) {
        loanService.createLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED)
                           .body(new ResponseDto(STATUS_201, MESSAGE_201));
    }

    @Operation(
        summary = "Get customer loan details",
        description = "Retrieves complete loan information for a customer using their mobile number. " +
                     "Returns loan details including amounts, payment status and outstanding balance.",
        operationId = "getLoanDetails"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Loan details retrieved successfully",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = LoansDto.class),
            examples = {
                @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Loan Details",
                    summary = "Complete loan information",
                    value = """
                    {
                      "mobileNumber": "1234567890",
                      "loanNumber": "L20240001",
                      "loanType": "PERSONAL",
                      "totalLoan": 50000,
                      "amountPaid": 15000,
                      "outstandingAmount": 35000
                    }
                    """
                )
            }
        )
    )
    @ApiAnnotations.StandardApiResponses
    @GetMapping("/fetch")
    public ResponseEntity<LoansDto> fetchLoanDetails(
        @ApiAnnotations.MobileNumberParam
        @RequestParam
        @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be exactly 10 digits")
        String mobileNumber
    ) {
        LoansDto loansDto = loanService.fetchLoan(mobileNumber);
        return ResponseEntity.ok(loansDto);
    }

    @Operation(
        summary = "Update customer loan details",
        description = "Updates existing loan information including payment details and loan terms. " +
                     "Allows modification of loan amounts and status information.",
        operationId = "updateLoan"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Loan updated successfully",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ResponseDto.class),
            examples = {
                @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Loan Updated",
                    summary = "Successful loan update",
                    value = """
                    {
                      "statusCode": "200",
                      "statusMsg": "Loan updated successfully"
                    }
                    """
                )
            }
        )
    )
    @ApiResponse(
        responseCode = "417",
        description = "Loan update failed",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ResponseDto.class),
            examples = {
                @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Update Failed",
                    summary = "Loan update operation failed",
                    value = """
                    {
                      "statusCode": "417",
                      "statusMsg": "Loan update failed"
                    }
                    """
                )
            }
        )
    )
    @ApiAnnotations.UpdateApiResponses
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateLoanDetails(
        @io.swagger.v3.oas.annotations.Parameter(
            description = "Complete loan information to update",
            required = true,
            schema = @Schema(implementation = LoansDto.class)
        )
        @Valid @RequestBody LoansDto loansDto
    ) {
        boolean isUpdated = loanService.updateLoan(loansDto);
        if (isUpdated) {
            return ResponseEntity.ok()
                               .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                               .body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
        summary = "Delete customer loan",
        description = "Deletes a customer's loan record from the system. " +
                     "This operation removes all loan information and payment history.",
        operationId = "deleteLoan"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Loan deleted successfully",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ResponseDto.class),
            examples = {
                @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Loan Deleted",
                    summary = "Successful loan deletion",
                    value = """
                    {
                      "statusCode": "200",
                      "statusMsg": "Loan deleted successfully"
                    }
                    """
                )
            }
        )
    )
    @ApiResponse(
        responseCode = "417",
        description = "Loan deletion failed",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = ResponseDto.class),
            examples = {
                @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Deletion Failed",
                    summary = "Loan deletion operation failed",
                    value = """
                    {
                      "statusCode": "417",
                      "statusMsg": "Loan deletion failed"
                    }
                    """
                )
            }
        )
    )
    @ApiAnnotations.DeleteApiResponses
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteLoanDetails(
        @ApiAnnotations.MobileNumberParam
        @RequestParam
        @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be exactly 10 digits")
        String mobileNumber
    ) {
        boolean isDeleted = loanService.deleteLoan(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.ok()
                               .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                               .body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_DELETE));
        }
    }
}
