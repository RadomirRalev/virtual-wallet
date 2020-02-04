package com.example.demo.controllers.mvccontrollers.transactions;

import com.example.demo.models.user.User;
import com.example.demo.models.wallet.Wallet;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.example.demo.helpers.UserHelper.currentPrincipalName;

@Controller
public class TransactionsController {
    private UserService userService;

    @Autowired
    public TransactionsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/transactions")
    public String account(Model model) {
        return "transactions";
    }

    @GetMapping("/deposit")
    public String makeDeposit(Model model) {
        User user = userService.getByUsername(currentPrincipalName());
        Wallet wallet = user.getWallets().get(0);
        model.addAttribute("user", user);
        model.addAttribute("wallet", wallet);
        return "deposit";
    }
}
