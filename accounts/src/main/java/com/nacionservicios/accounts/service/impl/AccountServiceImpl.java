package com.nacionservicios.accounts.service.impl;

import com.company.exceptionhandling.starter.domain.BusinessException;
import com.nacionservicios.accounts.dto.CustomerDto;
import com.nacionservicios.accounts.entity.Account;
import com.nacionservicios.accounts.entity.Customer;
import com.nacionservicios.accounts.mappers.AccountsMapper;
import com.nacionservicios.accounts.mappers.CustomerMapper;
import com.nacionservicios.accounts.repository.AccountRepository;
import com.nacionservicios.accounts.repository.CustomerRepository;
import com.nacionservicios.accounts.service.IAccountService;
import com.nacionservicios.accounts.starter.exceptionhandling.errors.AccountErrorCodes;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

import static com.nacionservicios.accounts.constants.AccountsConstants.SAVINGS;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final AccountsMapper accountsMapper;

    @Override
    public void createAccount(CustomerDto customerDto) {
        // Convert CustomerDto to Customer entity using the mapper

        customerRepository.findByMobileNumber(
                customerDto.mobileNumber()).ifPresent(customer -> {
            throw BusinessException.of(AccountErrorCodes.CUSTOMER_ALREADY_EXISTS,
                    "mobileNumber", customerDto.mobileNumber());
        });

        Customer customer = customerMapper.toCustomer(customerDto);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymus");

        // Save the customer to the database
        customerRepository.save(customer);
        accountRepository.save(createNewAccount(customer));
    }

    @Override
    public void getAccount(String mobileNumber) {

    }

    @Override
    public CustomerDto getAccounts(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> BusinessException.of(AccountErrorCodes.CUSTOMER_NOT_FOUND,
                        "mobileNumber", mobileNumber));

        Account account = accountRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> BusinessException.of(AccountErrorCodes.ACCOUNT_NOT_FOUND,
                        "customerId", customer.getCustomerId().toString()));

        return customerMapper.toCustomerDtoWithAccount(customer, account);

    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {

        Account account = accountRepository.findById(customerDto.accountDto().accountNumber())
                .orElseThrow(() -> BusinessException.of(AccountErrorCodes.ACCOUNT_NOT_FOUND,
                        "accountNumber", customerDto.accountDto().accountNumber().toString()));

        accountRepository.save(accountsMapper.toAccount(customerDto.accountDto()));

        Customer customer = customerRepository.findById(account.getCustomerId())
                .orElseThrow(() -> BusinessException.of(AccountErrorCodes.CUSTOMER_NOT_FOUND,
                        "getCustomerId", account.getCustomerId()));

        customerRepository.save(customerMapper.toCustomer(customerDto));

        return true;
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
