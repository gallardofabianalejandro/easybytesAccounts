package com.nacionservicios.accounts.mappers;

import com.nacionservicios.accounts.dto.CustomerDto;
import com.nacionservicios.accounts.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDto toCustomerDto(Customer customer);

    Customer toCustomer(CustomerDto customerDto);
}
