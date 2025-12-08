package com.nacionservicios.accounts.service;

import com.nacionservicios.accounts.dto.CustomerDto;

public interface IAccountService {

    void createAccount(CustomerDto customerDto);
}
