package com.example.demo.services;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InvalidCardException;
import com.example.demo.models.card.CardMapper;
import com.example.demo.models.card.CardRegistrationDTO;
import com.example.demo.models.card.CardDetails;
import com.example.demo.models.user.User;
import com.example.demo.repositories.CardDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static com.example.demo.constants.ExceptionConstants.*;
import static com.example.demo.helpers.CardHelper.isValidExpirationDate;

@Service
public class CardDetailsServiceImpl implements CardDetailsService {

    private CardDetailsRepository cardDetailsRepository;
    private UserService userService;
    private CardMapper cardMapper;

    @Autowired
    public CardDetailsServiceImpl(CardDetailsRepository cardDetailsRepository, UserService userService,
                                  CardMapper cardMapper) {
        this.cardDetailsRepository = cardDetailsRepository;
        this.userService = userService;
        this.cardMapper = cardMapper;
    }

    @Override
    public CardDetails getById(int id) {
        return cardDetailsRepository.getById(id);
    }

    @Override
    public CardDetails getByNumber(String number) {
        return cardDetailsRepository.getByNumber(number);
    }

    @Override
    public CardDetails createCard(CardRegistrationDTO cardRegistrationDTO, int userId) {
        cardRegistrationDTO.setUser_id(userId);

        if (isCardExist(cardRegistrationDTO.getCardNumber())) {
            throw new DuplicateEntityException(CARD_WITH_NUMBER_EXISTS, cardRegistrationDTO.getCardNumber());
        }

        if (!isValidExpirationDate(cardRegistrationDTO.getExpirationDate())) {
            throw new InvalidCardException(EXPIRATION_DATE_IS_INVALID);
        }

        if (!cardRegistrationDTO.getCardholderName().
                equalsIgnoreCase(userService.getById(userId).getFirstName() + " " + userService.getById(userId).getLastName())) {
            throw new InvalidCardException(THE_NAMES_DO_NOT_MATCH);
        }
        CardDetails cardDetails = cardMapper.mapCard(cardRegistrationDTO);
        return cardDetailsRepository.createCard(cardDetails);
    }

    //TODO
    @Override
    public CardDetails updateCard(CardRegistrationDTO cardRegistrationDTO) {
        throw new NotImplementedException();
    }

    @Override
    public void setCardStatus(String number, boolean status) {
        if (!isCardExist(number)) {
            throw new EntityNotFoundException(CARD_WITH_NUMBER_NOT_EXISTS, number);
        }
        cardDetailsRepository.setCardStatus(number, status);
    }

    @Override
    public boolean isCardExist(String number) {
        return cardDetailsRepository.isCardExist(number);
    }
}
