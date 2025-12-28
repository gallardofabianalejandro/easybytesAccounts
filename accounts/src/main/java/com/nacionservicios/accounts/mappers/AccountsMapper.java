package com.nacionservicios.accounts.mappers;

import com.nacionservicios.accounts.dto.AccountDto;
import com.nacionservicios.accounts.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountsMapper {

    AccountDto toAccountDto(Account account);

    // Para actualización: bloqueo total de campos sensibles
    @Mapping(target = "customerId", ignore = true)

    // Si la entidad Account tiene campos internos del starter, ignorarlos aquí también
    Account updateAccountFromDto(AccountDto dto, @MappingTarget Account account);
}
