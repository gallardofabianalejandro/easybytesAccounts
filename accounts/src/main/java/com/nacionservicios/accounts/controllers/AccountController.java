package com.nacionservicios.accounts.controllers;

import com.nacionservicios.accounts.dto.CustomerDto;
import com.nacionservicios.accounts.dto.ResponseDto;
import com.nacionservicios.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.nacionservicios.accounts.controllers.AccountsConstants.MESSAGE_201;
import static com.nacionservicios.accounts.controllers.AccountsConstants.STATUS_201;

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

}
