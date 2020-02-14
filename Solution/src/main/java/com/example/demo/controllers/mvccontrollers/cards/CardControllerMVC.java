package com.example.demo.controllers.mvccontrollers.cards;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.InvalidCardException;
import com.example.demo.models.card.CardDetails;
import com.example.demo.models.card.CardRegistrationDTO;
import com.example.demo.models.card.CardUpdateDTO;
import com.example.demo.models.user.User;
import com.example.demo.services.CardDetailsService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

import static com.example.demo.helpers.UserHelper.currentPrincipalName;

@Controller
public class CardControllerMVC {
    private CardDetailsService cardDetailsService;
    private UserService userService;

    @Autowired
    public CardControllerMVC(CardDetailsService cardDetailsService, UserService userService) {
        this.cardDetailsService = cardDetailsService;
        this.userService = userService;
    }

    @GetMapping("/card-registration")
    public String createCard(Model model) {
        User user = userService.getByUsername(currentPrincipalName());
        if (!user.isConfirm_identity()) {
            return "redirect:http://localhost:8181/confirm-identity";
        }
        model.addAttribute("CardRegistrationDTO", new CardRegistrationDTO());
        return "card/card-registration";
    }

    @PostMapping("/card-registration")
    public String createNewCard(@Valid @ModelAttribute("CardRegistrationDTO") CardRegistrationDTO cardRegistrationDTO,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "card/card-registration";
        }
        try {
            cardDetailsService.createCard(cardRegistrationDTO, currentPrincipalName());
        } catch (DuplicateEntityException | InvalidCardException e) {
            model.addAttribute("error", e.getMessage());
            return "card/card-registration";
        }
        return "messages/success-card-registration";
    }

    @GetMapping("/card/{cardId}")
    public String updateCard(@PathVariable int cardId, Model model) {
        User user = userService.getByUsername(currentPrincipalName());
        if (!cardDetailsService.isUserIsOwner(cardId, user.getId())) {
            return "access-denied";
        }

        if (!user.isConfirm_identity()) {
            return "redirect:http://localhost:8181/confirm-identity";
        }
        model.addAttribute("cardUpdateDTO", new CardUpdateDTO());
        return "card/update-card";
    }

    @PostMapping("/card/{cardId}")
    public String updateCard(@PathVariable int cardId,
                             @Valid @ModelAttribute("cardUpdateDTO") CardUpdateDTO cardUpdateDTO,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "card/update-card";
        }
        try {
            cardDetailsService.updateCard(cardUpdateDTO, cardId, currentPrincipalName());
        } catch (DuplicateEntityException | InvalidCardException e) {
            model.addAttribute("error", e.getMessage());
            return "card/update-card";
        }
        return "messages/success-card-update";
    }

}
