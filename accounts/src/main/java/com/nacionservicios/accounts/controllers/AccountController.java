package com.nacionservicios.accounts.controllers;

import com.nacionservicios.accounts.dto.CustomerDto;
import com.nacionservicios.accounts.dto.ResponseDto;
import com.nacionservicios.accounts.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.nacionservicios.accounts.constants.AccountsConstants.*;

@RestController
@RequestMapping(path = "/api/accounts", produces = "application/json")
@AllArgsConstructor
@Validated
@Tag(name = "Accounts", description = "Account management APIs for creating, retrieving, updating and deleting customer accounts")
public class AccountController {

    private final IAccountService accountService;


    @Operation(
            summary = "Create a new customer account",
            description = "Creates a new customer account with the provided customer and account information"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Customer already exists",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<ResponseDto> createAccount(
            @Parameter(description = "Customer information with account details", required = true)
            @Valid @RequestBody CustomerDto customerDto) {
        accountService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(STATUS_201, MESSAGE_201));
    }

    @Operation(
            summary = "Get customer account details",
            description = "Retrieves customer and account information by mobile number"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account details retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid mobile number format",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content)
    })
    @GetMapping("/{mobileNumber}")
    public ResponseEntity<CustomerDto> getAccount(
            @Parameter(description = "Customer's mobile number (10 digits)", example = "1234567890", required = true)
            @Pattern(regexp = "^\\d{10}$" , message = "Mobile number is numeric and 10 digits") @PathVariable String mobileNumber) {
        return ResponseEntity.ok(accountService.getAccounts(mobileNumber));

    }

    @Operation(
            summary = "Update customer account",
            description = "Updates an existing customer account with new information"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "304", description = "No changes made to the account",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content)
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccount(
            @Parameter(description = "Updated customer information with account details", required = true)
            @RequestBody CustomerDto customerDto) {
        boolean isUpdated = accountService.updateAccount(customerDto);
        if (isUpdated) {
            return ResponseEntity.ok(new ResponseDto(STATUS_200, MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                    .body(new ResponseDto(STATUS_304, MESSAGE_304));
        }
    }

    @Operation(
            summary = "Delete customer account",
            description = "Deletes a customer account by mobile number"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account deleted successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "304", description = "Account could not be deleted",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid mobile number format",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content)
    })
    @DeleteMapping("/{mobileNumber}")
    public ResponseEntity<ResponseDto> deleteAccount(
            @Parameter(description = "Customer's mobile number (10 digits)", example = "1234567890", required = true)
            @PathVariable @Pattern(regexp = "^\\d{10}$" , message = "Mobile number is numeric and 10 digits") String mobileNumber) {
        boolean isDeleted = accountService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.ok(new ResponseDto(STATUS_200, MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                    .body(new ResponseDto(STATUS_304, MESSAGE_304));
        }
    }
}
