package com.nacionservicios.accounts.controllers;

import com.nacionservicios.accounts.dto.CustomerDto;
import com.nacionservicios.accounts.dto.ResponseDto;
import com.nacionservicios.accounts.service.IAccountService;
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
public class AccountController {

    private final IAccountService accountService;


    @PostMapping
    public ResponseEntity<ResponseDto> createAccount( @Valid @RequestBody CustomerDto customerDto) {
        accountService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(STATUS_201, MESSAGE_201));
    }

    @GetMapping("/{mobileNumber}")
    public ResponseEntity<CustomerDto> getAccount(@Pattern(regexp = "^\\d{10}$" , message = "Mobile number is numeric and 10 digits") @PathVariable String mobileNumber) {
        return ResponseEntity.ok(accountService.getAccounts(mobileNumber));

    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccount(@RequestBody CustomerDto customerDto) {
        boolean isUpdated = accountService.updateAccount(customerDto);
        if (isUpdated) {
            return ResponseEntity.ok(new ResponseDto(STATUS_200, MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                    .body(new ResponseDto(STATUS_304, MESSAGE_304));
        }
    }

    @DeleteMapping("/{mobileNumber}")
    public ResponseEntity<ResponseDto> deleteAccount(@PathVariable
                                                         @Pattern(regexp = "^\\d{10}$" , message = "Mobile number is numeric and 10 digits") String mobileNumber) {
        boolean isDeleted = accountService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.ok(new ResponseDto(STATUS_200, MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                    .body(new ResponseDto(STATUS_304, MESSAGE_304));
        }
    }
}
