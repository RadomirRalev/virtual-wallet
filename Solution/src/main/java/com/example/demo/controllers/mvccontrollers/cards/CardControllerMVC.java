package com.example.demo.controllers.mvccontrollers.cards;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.InvalidCardException;
import com.example.demo.models.card.CardRegistrationDTO;
import com.example.demo.services.CardDetailsService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @GetMapping("/cardregistration")
    public String createCard(Model model) {
        model.addAttribute("CardRegistrationDTO", new CardRegistrationDTO());
        return "cardregistration";
    }

    @PostMapping("/cardregistration")
    public String createNewCard(@Valid @ModelAttribute("CardRegistrationDTO") CardRegistrationDTO cardRegistrationDTO,
                                BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "cardregistration";
        }

        int userId = userService.getByUsername(currentPrincipalName()).getId();

        try {
            cardDetailsService.createCard(cardRegistrationDTO, userId);
        } catch (DuplicateEntityException | InvalidCardException e) {
            model.addAttribute("error", e.getMessage());
            return "cardregistration";
        }
        return "messages/success-card-registration";
    }
}
