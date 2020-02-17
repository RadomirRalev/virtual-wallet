package com.example.demo.services.implementations;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InvalidCardException;
import com.example.demo.exceptions.InvalidPermission;
import com.example.demo.models.card.CardMapper;
import com.example.demo.models.card.CardRegistrationDTO;
import com.example.demo.models.card.CardDetails;
import com.example.demo.models.card.CardUpdateDTO;
import com.example.demo.models.user.User;
import com.example.demo.repositories.contracts.CardDetailsRepository;
import com.example.demo.services.contracts.CardDetailsService;
import com.example.demo.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (!checkIfCardIdExists(id)) {
            throw new EntityNotFoundException(CARD_WITH_ID_NOT_EXISTS, id);
        }
        return cardDetailsRepository.getById(id);
    }

    @Override
    public CardDetails getByNumber(String number) {
        if (!checkIfCardNumberExists(number)) {
            throw new EntityNotFoundException(CARD_WITH_NUMBER_NOT_EXISTS, number);
        }
        return cardDetailsRepository.getByNumber(number);
    }

    @Override
    public CardDetails createCard(CardRegistrationDTO cardRegistrationDTO, String username) {
        User cardOwner = userService.getByUsername(username);
        if (!cardOwner.isConfirm_identity()) {
            throw new InvalidPermission(CONFIRM_YOUR_IDENTITY_TO_REGISTER_CARD, username);
        }

        if (!cardRegistrationDTO.getCardholderName().
                equalsIgnoreCase(cardOwner.getFirstName() + " " + cardOwner.getLastName())) {
            throw new InvalidCardException(THE_NAMES_DO_NOT_MATCH);
        }

        if (isCardExist(cardRegistrationDTO.getCardNumber())) {
            throw new DuplicateEntityException(CARD_WITH_NUMBER_EXISTS, cardRegistrationDTO.getCardNumber());
        }

        if (!isValidExpirationDate(cardRegistrationDTO.getExpirationDate())) {
            throw new InvalidCardException(EXPIRATION_DATE_IS_INVALID);
        }

        cardRegistrationDTO.setUser_id(cardOwner.getId());
        CardDetails cardDetails = cardMapper.mapCard(cardRegistrationDTO);
        return cardDetailsRepository.createCard(cardDetails);
    }

    @Override
    public CardDetails updateCard(CardUpdateDTO cardUpdateDTO, int updatedCardId, String username) {
        CardDetails cardToUpdate = getById(updatedCardId);
        User currentUser = userService.getByUsername(username);
        if (cardToUpdate.getUser().getId() != currentUser.getId()) {
            throw new InvalidPermission(USER_HAVE_NOT_PERMISSION_TO_UPDATE_CARD, currentUser.getUsername());
        }

        CardMapper.updateCard(cardToUpdate, cardUpdateDTO);
        return cardDetailsRepository.updateCard(cardToUpdate);
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
        return cardDetailsRepository.checkIfCardNumberExists(number);
    }

    @Override
    public boolean isUserIsOwner(int cardId, int userId) {
        return cardDetailsRepository.isUserIsOwner(cardId, userId);
    }

    @Override
    public boolean checkIfCardIdValid(int cardId) {
        return cardDetailsRepository.checkIfCardIdValid(cardId);
    }

    public boolean checkIfCardIdExists(int cardId) {
        return cardDetailsRepository.checkIfCardIdExists(cardId);
    }

    public boolean checkIfCardNumberExists(String cardNumber) {
        return cardDetailsRepository.checkIfCardNumberExists(cardNumber);
    }
}
