package com.example.demo.controllers.mvccontrollers.transactions;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.InvalidTransactionException;
import com.example.demo.models.transaction.TransactionDTO;
import com.example.demo.models.user.User;
import com.example.demo.models.wallet.Wallet;
import com.example.demo.services.TransactionService;
import com.example.demo.services.UserService;
import com.example.demo.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import static com.example.demo.helpers.UserHelper.currentPrincipalName;

@Controller
public class TransactionsController {
    private UserService userService;
    private TransactionService transactionService;
    private WalletService walletService;


    @Autowired
    public TransactionsController(UserService userService, TransactionService transactionService, WalletService walletService) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.walletService = walletService;
    }

    @GetMapping("/transactions")
    public String transactions(Model model) {
        return "transactions";
    }

    @GetMapping("/deposit")
    public String makeDeposit(Model model) {
        User user = userService.getByUsername(currentPrincipalName());
        double availableSum = userService.getAvailableSum(user.getId());
        model.addAttribute("availableSum", availableSum);
        model.addAttribute("depositDTO", new TransactionDTO());
        model.addAttribute("user", user);
        return "deposit";
    }

    @PostMapping("/deposit")
    public String createDeposit(@Valid @ModelAttribute("depositDTO") TransactionDTO transactionDTO,
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
        return "redirect:mywallets";
    }

    @GetMapping("/withdrawal")
    public String makeWithdrawal(Model model) {
        User user = userService.getByUsername(currentPrincipalName());
        double availableSum = userService.getAvailableSum(user.getId());
        model.addAttribute("availableSum", availableSum);
        model.addAttribute("withdrawalDTO", new TransactionDTO());
        model.addAttribute("user", user);
        return "withdrawal";
    }

    @PostMapping("/withdrawal")
    public String createWithdrawal(@Valid @ModelAttribute("withdrawalDTO") TransactionDTO transactionDTO,
                                BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "withdrawal";
        }
        try {
            transactionService.createWithdrawal(transactionDTO);
        } catch (DuplicateEntityException | InvalidTransactionException e) {
            model.addAttribute("error", e.getMessage());
            return "withdrawal";
        }
        return "redirect:mywallets";
    }

    @GetMapping("/walletstransaction")
    public String makeWalletToWalletTransaction(@RequestParam int receiverId,
            Model model) {
        User sender = userService.getByUsername(currentPrincipalName());
        User receiver = userService.getById(receiverId);
        Wallet receiverWallet= walletService.getDefaultWallet(receiverId);
        double availableSum = userService.getAvailableSum(sender.getId());
        model.addAttribute("availableSum", availableSum);
        model.addAttribute("walletsTransactionDTO", new TransactionDTO());
        model.addAttribute("sender", sender);
        model.addAttribute("receiver", receiver);
        model.addAttribute("receiverWallet", receiverWallet);
        return "walletstransaction";
    }

    @PostMapping("/walletstransaction")
    public String createWalletToWalletTransaction(@Valid @ModelAttribute("walletsTransactionDTO") TransactionDTO transactionDTO,
                                   BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "walletstransaction";
        }
        try {
            transactionService.createInternal(transactionDTO);
        } catch (DuplicateEntityException | InvalidTransactionException e) {
            model.addAttribute("error", e.getMessage());
            return "walletstransaction";
        }
        return "redirect:mywallets";
    }
}
