package com.nacionservicios.accounts.mappers;

import com.nacionservicios.accounts.dto.AccountDto;
import com.nacionservicios.accounts.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountsMapper {

    AccountDto toAccountDto(Account account);

    Account toAccount(AccountDto accountDto);
}
