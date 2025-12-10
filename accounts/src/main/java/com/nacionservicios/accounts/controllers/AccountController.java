package com.nacionservicios.accounts.controllers;

import com.nacionservicios.accounts.dto.CustomerDto;
import com.nacionservicios.accounts.dto.ResponseDto;
import com.nacionservicios.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.nacionservicios.accounts.constants.AccountsConstants.*;

@RestController
@RequestMapping(path = "/api/accounts", produces = "application/json")
@AllArgsConstructor
public class AccountController {

    private final IAccountService accountService;


    @PostMapping
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto) {
        accountService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(STATUS_201, MESSAGE_201));
    }

    @GetMapping("/{mobileNumber}")
    public ResponseEntity<CustomerDto> getAccount(@PathVariable String mobileNumber) {
        return ResponseEntity.ok(accountService.getAccounts(mobileNumber));

    }

}
