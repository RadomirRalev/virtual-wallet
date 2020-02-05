package com.example.demo.controllers.mvccontrollers.transactions;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.InvalidOptionalFieldParameter;
import com.example.demo.exceptions.InvalidPasswordException;
import com.example.demo.exceptions.InvalidTransactionException;
import com.example.demo.models.transaction.TransactionDTO;
import com.example.demo.models.user.User;
import com.example.demo.models.user.UserRegistrationDTO;
import com.example.demo.models.wallet.Wallet;
import com.example.demo.services.TransactionService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.IOException;

import static com.example.demo.helpers.UserHelper.currentPrincipalName;

@Controller
public class TransactionsController {
    private UserService userService;
    private TransactionService transactionService;


    @Autowired
    public TransactionsController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions")
    public String account(Model model) {
        return "transactions";
    }

    @GetMapping("/deposit")
    public String makeDeposit(Model model) {
        User user = userService.getByUsername(currentPrincipalName());
        Wallet wallet = user.getWallets().get(0);
        model.addAttribute("depositDTO", new TransactionDTO());
        model.addAttribute("user", user);
        model.addAttribute("wallet", wallet);
        return "deposit";
    }

    @PostMapping("/deposit")
    public String createDeposit(@Valid @ModelAttribute("depositDTO") TransactionDTO transactionDTO,
                             @Valid @ModelAttribute("wallet") Wallet wallet,
                             BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "deposit";
        }
        try {
            transactionService.createDeposit(transactionDTO);
        } catch (DuplicateEntityException | InvalidTransactionException e) {
            model.addAttribute("error", e.getMessage());
            return "deposit";
        }
        return "successcardregistration";
    }
}
