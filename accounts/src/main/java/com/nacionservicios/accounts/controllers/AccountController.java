package com.nacionservicios.accounts.controllers;

import com.openapi.autoconfigure.openapi.ApiAnnotations;
import com.nacionservicios.accounts.dto.CustomerDto;
import com.nacionservicios.accounts.dto.ResponseDto;
import com.nacionservicios.accounts.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
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
    @ApiAnnotations.CreateApiResponses
    @PostMapping
    public ResponseEntity<ResponseDto> createAccount(
            @ApiAnnotations.CustomerDataParam
            @Valid @RequestBody CustomerDto customerDto) {
        accountService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(STATUS_201, MESSAGE_201));
    }

    @Operation(
            summary = "Get customer account details",
            description = "Retrieves customer and account information by mobile number"
    )
    @ApiAnnotations.StandardApiResponses
    @GetMapping("/{mobileNumber}")
    public ResponseEntity<CustomerDto> getAccount(
            @ApiAnnotations.MobileNumberParam
            @Pattern(regexp = "^\\d{10}$" , message = "Mobile number is numeric and 10 digits") @PathVariable String mobileNumber) {
        return ResponseEntity.ok(accountService.getAccounts(mobileNumber));

    }

    @Operation(
            summary = "Update customer account",
            description = "Updates an existing customer account with new information"
    )
    @ApiAnnotations.UpdateApiResponses
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccount(
            @ApiAnnotations.CustomerDataParam
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
    @ApiAnnotations.DeleteApiResponses
    @DeleteMapping("/{mobileNumber}")
    public ResponseEntity<ResponseDto> deleteAccount(
            @ApiAnnotations.MobileNumberParam
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
