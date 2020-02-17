package com.example.demo.controllers.restcontrollers;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InvalidCardException;
import com.example.demo.models.card.CardRegistrationDTO;
import com.example.demo.models.card.CardDetails;
import com.example.demo.models.card.CardUpdateDTO;
import com.example.demo.services.contracts.CardDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import static com.example.demo.constants.SQLQueryConstants.DISABLE;
import static com.example.demo.helpers.UserHelper.currentPrincipalName;

@RestController
@RequestMapping("api/cards")

public class CardController {

    private CardDetailsService cardDetailsService;

    @Autowired
    public CardController(CardDetailsService cardDetailsService) {
        this.cardDetailsService = cardDetailsService;
    }

    @GetMapping("/{id}")
    public CardDetails getById(@PathVariable int id) {
        try {
            return cardDetailsService.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/number/{number}")
    public CardDetails getByNumber(@PathVariable String number) {
        try {
            return cardDetailsService.getByNumber(number);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/")
    public CardDetails create(@RequestBody @Valid CardRegistrationDTO cardRegistrationDTO) {
        try {
            return cardDetailsService.createCard(cardRegistrationDTO, currentPrincipalName());
        } catch (DuplicateEntityException | InvalidCardException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/card/{cardId}")
    public CardDetails updateCard(@PathVariable int cardId,
                             @Valid @ModelAttribute("cardUpdateDTO") CardUpdateDTO cardUpdateDTO) {
        try {
           return cardDetailsService.updateCard(cardUpdateDTO, cardId, currentPrincipalName());
        } catch (DuplicateEntityException | InvalidCardException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/number/{number}")
    public void deleteUser(@PathVariable String number) {
        try {
            cardDetailsService.setCardStatus(number, DISABLE);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
