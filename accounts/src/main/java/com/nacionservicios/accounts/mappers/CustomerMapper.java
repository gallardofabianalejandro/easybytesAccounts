package com.nacionservicios.accounts.mappers;

import com.nacionservicios.accounts.dto.CustomerDto;
import com.nacionservicios.accounts.entity.Account;
import com.nacionservicios.accounts.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AccountsMapper.class})
public interface CustomerMapper {

    CustomerDto toCustomerDto(Customer customer);

    Customer toCustomer(CustomerDto customerDto);

    @Mapping(target = "accountDto", source = "account")
    CustomerDto toCustomerDtoWithAccount(Customer customer, Account account);

}
