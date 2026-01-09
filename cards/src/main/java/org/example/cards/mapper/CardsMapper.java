package org.example.cards.mapper;

import org.example.cards.dto.CardsDto;
import org.example.cards.entity.Cards;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CardsMapper {
    Cards toEntity(CardsDto cardsDto);

    CardsDto toDto(Cards cards);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Cards partialUpdate(CardsDto cardsDto, @MappingTarget Cards cards);
}