package com.nacionservicios.accounts.service.impl;

import com.nacionservicios.accounts.dto.CustomerDto;
import com.nacionservicios.accounts.entity.Account;
import com.nacionservicios.accounts.entity.Customer;
import com.nacionservicios.accounts.exceptions.CustomerAlreadyExistsException;
import com.nacionservicios.accounts.mappers.CustomerMapper;
import com.nacionservicios.accounts.repository.AccountRepository;
import com.nacionservicios.accounts.repository.CustomerRepository;
import com.nacionservicios.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import static com.nacionservicios.accounts.controllers.AccountsConstants.SAVINGS;
@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public void createAccount(CustomerDto customerDto) {
        // Convert CustomerDto to Customer entity using the mapper
        Customer customer = customerMapper.toCustomer(customerDto);

        Optional<Customer> customerOptional = customerRepository.findByMobileNumber(customerDto.mobileNumber());
        if (customerOptional.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already exists with mobile number: " + customerDto.mobileNumber());
        }

        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymus");

        // Save the customer to the database
        customerRepository.save(customer);
        accountRepository.save(createNewAccount(customer));
    }


    private Account createNewAccount(Customer customer) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 100000000L + new Random().nextInt(90000000);
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(SAVINGS);
        newAccount.setBranchAddress(customer.getEmail());
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Anonymus");
        return newAccount;


    }
}
