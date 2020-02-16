package com.example.demo.controllers.mvccontrollers.wallets;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.InvalidWalletException;
import com.example.demo.models.user.User;
import com.example.demo.models.wallet.Wallet;
import com.example.demo.models.wallet.WalletCreationDTO;
import com.example.demo.models.wallet.WalletUpdateDTO;
import com.example.demo.services.UserService;
import com.example.demo.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

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
    public String createNewWallet(@Valid @ModelAttribute("WalletCreationDTO") WalletCreationDTO walletCreationDTO,
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
        return "redirect:/mywallets";
    }

    @GetMapping("/mywallets")
    public String myWallets(Model model) {
        int userId = userService.getByUsername(currentPrincipalName()).getId();
        List<Wallet> walletList = walletService.getWalletsbyUserId(userId);
        model.addAttribute("walletList", walletList);
        return "mywallets";
    }

    @GetMapping("/mywallets/editwallet")
    public String editWallet(@RequestParam int id, Model model) {
        Wallet wallet = walletService.getWalletById(id);
        WalletUpdateDTO walletUpdateDTO = new WalletUpdateDTO();
        model.addAttribute("wallet", wallet);
        model.addAttribute("walletUpdateDTO", walletUpdateDTO);
        return "editwallet";
    }

    @PostMapping("mywallets/editwallet")
    public String updateWallet(@ModelAttribute("walletUpdateDTO") WalletUpdateDTO walletUpdateDTO) {
        Wallet walletToUpdate = walletService.getWalletById(walletUpdateDTO.getId());
        walletToUpdate.setName(walletUpdateDTO.getName());
        walletService.updateWallet(walletToUpdate);
        return "redirect:/mywallets";
    }

    @GetMapping("/mywallets/setdefault")
    public String setDefault(@RequestParam int id, Model model) {
        Wallet wallet = walletService.getWalletById(id);
        User user = wallet.getUser();
        if (!user.getUsername().equals(currentPrincipalName())) {
            return "messages/access-denied";
        }
        WalletUpdateDTO walletUpdateDTO = new WalletUpdateDTO();
        model.addAttribute("wallet", wallet);
        model.addAttribute("walletUpdateDTO", walletUpdateDTO);
        return "setdefault";
    }

    @PostMapping("mywallets/setdefault")
    public String setAsDefault(@ModelAttribute("walletUpdateDTO") WalletUpdateDTO walletUpdateDTO) {
        Wallet walletToUpdate = walletService.getWalletById(walletUpdateDTO.getId());
        User user = walletToUpdate.getUser();
        walletService.setAsDefault(walletToUpdate, user);
        return "redirect:/mywallets";
    }
}