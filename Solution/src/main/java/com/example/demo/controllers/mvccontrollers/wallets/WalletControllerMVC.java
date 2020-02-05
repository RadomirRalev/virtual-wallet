package com.example.demo.controllers.mvccontrollers.wallets;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.InvalidCardException;
import com.example.demo.exceptions.InvalidWalletException;
import com.example.demo.models.card.CardRegistrationDTO;
import com.example.demo.models.wallet.WalletCreationDTO;
import com.example.demo.services.CardDetailsService;
import com.example.demo.services.UserService;
import com.example.demo.services.WalletService;
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
public class WalletControllerMVC {
    private WalletService walletService;
    private UserService userService;

    @Autowired
    public WalletControllerMVC(UserService userService, WalletService walletService) {
        this.userService = userService;
        this.walletService = walletService;
    }

    @GetMapping("/createwallet")
    public String createWallet(Model model) {
        model.addAttribute("WalletCreationDTO", new WalletCreationDTO());
        return "createwallet";
    }

    @PostMapping("/createwallet")
    public String createNewCard(@Valid @ModelAttribute("WalletCreationDTO") WalletCreationDTO walletCreationDTO,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "createwallet";
        }
        int userId = userService.getByUsername(currentPrincipalName()).getId();
        try {
            walletService.createWallet(walletCreationDTO, userId);
        } catch (DuplicateEntityException | InvalidWalletException e) {
            model.addAttribute("error", e.getMessage());
            return "createwallet";
        }
        return "successcardregistration";
    }
}
