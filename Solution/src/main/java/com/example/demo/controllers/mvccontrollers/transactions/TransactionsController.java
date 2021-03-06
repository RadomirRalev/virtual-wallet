package com.example.demo.controllers.mvccontrollers.transactions;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.InsufficientFundsException;
import com.example.demo.exceptions.InvalidPermission;
import com.example.demo.exceptions.InvalidTransactionException;
import com.example.demo.models.transaction.TransactionDTO;
import com.example.demo.models.user.User;
import com.example.demo.models.wallet.Wallet;
import com.example.demo.services.contracts.TransactionService;
import com.example.demo.services.contracts.UserService;
import com.example.demo.services.contracts.WalletService;
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
        if (user.isBlocked()) {
            return "messages/access-denied-sender-blocked";
        }
        String availableSum = userService.getAvailableSum(user.getId());
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
            transactionService.createDeposit(transactionDTO, currentPrincipalName());
        } catch (DuplicateEntityException | InvalidTransactionException | InvalidPermission e) {
            model.addAttribute("error", e.getMessage());
            return "deposit";
        }
        return "redirect:mywallets";
    }

    @GetMapping("/withdrawal")
    public String makeWithdrawal(Model model) {
        User user = userService.getByUsername(currentPrincipalName());
        if (user.isBlocked()) {
            return "messages/access-denied-sender-blocked";
        }
        String availableSum = userService.getAvailableSum(user.getId());
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
            transactionService.createWithdrawal(transactionDTO, currentPrincipalName());
        } catch (DuplicateEntityException | InvalidTransactionException | InvalidPermission |
                InsufficientFundsException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
        return "redirect:mywallets";
    }

    @GetMapping("walletstransaction")
    public String makeWalletToWalletTransaction(@RequestParam int receiverId,
                                                Model model) {
        User sender = userService.getByUsername(currentPrincipalName());
        if (sender.isBlocked()) {
            return "messages/access-denied-sender-blocked";
        }
        User receiver = userService.getById(receiverId);
        if (receiver.isBlocked()) {
            return "messages/access-denied-receiver-blocked";
        }
        Wallet receiverWallet = walletService.getDefaultWallet(receiverId);
        String availableSum = userService.getAvailableSum(sender.getId());
        model.addAttribute("availableSum", availableSum);
        model.addAttribute("walletsTransactionDTO", new TransactionDTO());
        model.addAttribute("sender", sender);
        model.addAttribute("receiver", receiver);
        model.addAttribute("receiverWallet", receiverWallet);
        return "walletstransaction";
    }

    @PostMapping("walletstransaction")
    public String createWalletToWalletTransaction(@Valid @ModelAttribute("walletsTransactionDTO") TransactionDTO transactionDTO,
                                                  @ModelAttribute("receiver") User receiver,
                                                  BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "walletstransaction";
        }
        try {
            transactionService.createInternal(transactionDTO, currentPrincipalName());
        } catch (DuplicateEntityException | InvalidTransactionException | InvalidPermission |
                InsufficientFundsException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
        return "redirect:mywallets";
    }

    @GetMapping("betweenmywallets")
    public String makeTransactionBetweenMyWallets(@RequestParam int walletReceiverId,
                                                Model model) {
        User sender = userService.getByUsername(currentPrincipalName());
        if (sender.isBlocked()) {
            return "messages/access-denied-sender-blocked";
        }
        Wallet receiverWallet = walletService.getWalletById(walletReceiverId);
        String availableSum = userService.getAvailableSum(sender.getId());
        model.addAttribute("availableSum", availableSum);
        model.addAttribute("walletsTransactionDTO", new TransactionDTO());
        model.addAttribute("sender", sender);
        model.addAttribute("receiverWallet", receiverWallet);
        return "betweenmywallets";
    }

    @PostMapping("betweenmywallets")
    public String createTransactionBetweenMyWallets(@Valid @ModelAttribute("walletsTransactionDTO") TransactionDTO walletsTransactionDTO,
                                                  @ModelAttribute("receiverWallet") Wallet receiverWallet,
                                                  @ModelAttribute("sender") User sender,
                                                  BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "mywallets";
        }
        try {
            transactionService.createInternal(walletsTransactionDTO, currentPrincipalName());
        } catch (DuplicateEntityException | InvalidTransactionException | InvalidPermission |
                InsufficientFundsException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
        return "redirect:mywallets";
    }
}
