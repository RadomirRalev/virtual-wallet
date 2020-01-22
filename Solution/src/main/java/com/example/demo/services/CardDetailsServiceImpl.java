package com.example.demo.services;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InvalidCardException;
import com.example.demo.models.card.CardMapper;
import com.example.demo.models.card.CardDTO;
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
    public CardDetails createCard(CardDTO cardDTO, String username) {
        User user = userService.getByUsername(username);

        if (isCardExist(cardDTO.getCardNumber())) {
            throw new DuplicateEntityException(CARD_WITH_NUMBER_EXISTS, cardDTO.getCardNumber());
        }

        if(!isValidExpirationDate(cardDTO.getExpirationDate())){
            throw new InvalidCardException(EXPIRATION_DATE_IS_INVALID);
        }

        if (!cardDTO.getCardholderName().equalsIgnoreCase(user.getFirstName() + " " + user.getLastName())){
            throw new InvalidCardException(THE_NAMES_DO_NOT_MATCH);
        }
        CardDetails cardDetails = cardMapper.mapCard(cardDTO);

        return cardDetailsRepository.createCard(cardDetails, user);
    }

    //TODO
    @Override
    public CardDetails updateCard(CardDTO cardDTO) {
        throw new NotImplementedException();
    }

    @Override
    public void setCardStatus(String number, int status) {
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
