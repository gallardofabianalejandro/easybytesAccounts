package org.example.loans.entity;

import org.example.loans.dto.LoansDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LoansMapper {
    Loans toEntity(LoansDto loansDto);

    LoansDto toDto(Loans loans);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Loans partialUpdate(LoansDto loansDto, @MappingTarget Loans loans);
}