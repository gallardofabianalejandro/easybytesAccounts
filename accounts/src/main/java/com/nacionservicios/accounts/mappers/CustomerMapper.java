package com.nacionservicios.accounts.mappers;

import com.nacionservicios.accounts.dto.AccountDto;
import com.nacionservicios.accounts.dto.CustomerDto;
import com.nacionservicios.accounts.entity.Account;
import com.nacionservicios.accounts.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {AccountsMapper.class})
public interface CustomerMapper {

    CustomerDto toCustomerDto(Customer customer);

    @Mapping(target = "customerId", ignore = true)
    Customer toCustomer(CustomerDto dto, @MappingTarget Customer customer);

    @Mapping(target = "accountDto", source = "account")
    CustomerDto toCustomerDtoWithAccount(Customer customer, Account account);

}
