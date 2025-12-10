package com.nacionservicios.accounts.service;

import com.nacionservicios.accounts.dto.CustomerDto;
import org.springframework.http.ResponseEntity;

public interface IAccountService {

    void createAccount(CustomerDto customerDto);

    void getAccount(String mobileNumber);

    CustomerDto getAccounts(String mobileNumber);
}
