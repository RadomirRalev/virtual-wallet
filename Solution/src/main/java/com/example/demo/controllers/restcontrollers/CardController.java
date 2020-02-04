package com.example.demo.controllers.restcontrollers;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InvalidCardException;
import com.example.demo.models.card.CardRegistrationDTO;
import com.example.demo.models.card.CardDetails;
import com.example.demo.services.CardDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import static com.example.demo.constants.SQLQueryConstants.DISABLE;

@RestController
@RequestMapping("api/card")

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

    @PostMapping("/{username}")
    public CardDetails create(@RequestBody @Valid CardRegistrationDTO cardRegistrationDTO, @PathVariable int userId) {
        try {
            return cardDetailsService.createCard(cardRegistrationDTO, userId);
        } catch (DuplicateEntityException | InvalidCardException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
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
