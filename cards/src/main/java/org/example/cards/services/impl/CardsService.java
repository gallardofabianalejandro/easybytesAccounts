package org.example.cards.services.impl;

import com.company.exceptionhandling.starter.domain.BusinessException;
import org.example.cards.constants.CardsConstants;
import org.example.cards.dto.CardsDto;
import org.example.cards.entity.Cards;
import org.example.cards.exceptions.CardAlreadyExistsException;
import org.example.cards.exceptions.CardsErrorCodes;
import org.example.cards.mapper.CardsMapper;
import org.example.cards.repositories.CardsRepository;
import org.example.cards.services.ICardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class CardsService implements ICardsService {
    private final CardsRepository cardsRepository;
    private final CardsMapper cardsMapper;

    public CardsService(CardsRepository cardsRepository, CardsMapper cardsMapper) {
        this.cardsRepository = cardsRepository;
        this.cardsMapper = cardsMapper;
    }


    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> optionalCards= cardsRepository.findByMobileNumber(mobileNumber);
        if(optionalCards.isPresent()){
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber "+mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new card details
     */
    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }

    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> BusinessException.of(CardsErrorCodes.CARD_NOT_FOUND, "mobileNumber", mobileNumber)
        );
        return cardsMapper.toDto(cards);
    }

    @Override
    public boolean updateCard(CardsDto cardsDto) {
        Cards cards = cardsRepository.findByCardNumber(cardsDto.cardNumber()).orElseThrow(
                () -> BusinessException.of(CardsErrorCodes.CARD_NOT_FOUND, "cardNumber", cardsDto.cardNumber())
        );

        cardsRepository.save(cardsMapper.partialUpdate(cardsDto, cards));
        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> BusinessException.of(CardsErrorCodes.CARD_NOT_FOUND, "mobileNumber", mobileNumber)
        );
        cardsRepository.delete(cards);
        return true;
    }
}


