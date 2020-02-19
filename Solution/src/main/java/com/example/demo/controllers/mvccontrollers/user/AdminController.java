package com.example.demo.controllers.mvccontrollers.user;

import com.example.demo.models.transaction.Transaction;
import com.example.demo.models.transaction.TransactionFilterDTO;
import com.example.demo.models.user.User;
import com.example.demo.services.contracts.TransactionService;
import com.example.demo.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static com.example.demo.constants.SQLQueryConstants.DISABLE;
import static com.example.demo.constants.SQLQueryConstants.ENABLE;
import static com.example.demo.helpers.UserHelper.currentPrincipalName;


@Controller
public class AdminController {
    private UserService userService;
    private TransactionService transactionService;
    private String currentSearchUrl;

    @Autowired
    public AdminController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping("/admin")
    public String profile(Model model) {
        List<User> userToConfirm = userService.getUsersForConfirm();
        model.addAttribute("userToConfirm", userToConfirm);
        return "admin/home";
    }

    @GetMapping("admin/search")
    public String filterUsers(@RequestParam String text,
                              @RequestParam(value = "criterium") String criterium, Model model, HttpServletRequest request) {
        List<User> searchResult;
        currentSearchUrl = request.getRequestURL().toString() + "?" + request.getQueryString();
        switch (criterium) {
            case "username":
                searchResult = userService.searchByUsernameAsAdmin(text);
                model.addAttribute("users", searchResult);
                break;
            case "phonenumber":
                searchResult = userService.searchByPhoneNumberAsAdmin(text);
                model.addAttribute("users", searchResult);
                break;
            case "email":
                searchResult = userService.searchByEmailAsAdmin(text);
                model.addAttribute("users", searchResult);
                break;
        }
        return "admin/search-results";
    }

    @PostMapping("/admin/{username}/blocked")
    public String AdminBlockedUser(@PathVariable("username") String username) {
        userService.setBlockedStatus(username, ENABLE);
        return "redirect:" + currentSearchUrl;
    }

    @PostMapping("/admin/{username}/un-blocked")
    public String AdminUnBlockedUser(@PathVariable("username") String username) {
        userService.setBlockedStatus(username, DISABLE);
        return "redirect:" + currentSearchUrl;
    }

    @PostMapping("/admin/{username}/disable")
    public String AdminDeleteUser(@PathVariable("username") String username) {
        userService.setStatusUser(username, DISABLE);
        return "redirect:" + currentSearchUrl;
    }

    @PostMapping("/admin/{username}/enable")
    public String AdminEnableUser(@PathVariable("username") String username) {
        userService.setStatusUser(username, ENABLE);
        return "redirect:" + currentSearchUrl;
    }

    @GetMapping("/admin/{username}/transaction-history")
    public String getTransactionHistory(@PathVariable String username, @RequestParam(required = false, defaultValue = "1") Integer page,
                                        Model model) {
        User user = userService.getByUsername(username);
        List<Transaction> transactionHistory = transactionService.getTransactionsByUserId(user.getId(), page);
        boolean isNextEmpty = transactionService.getTransactionsByUserId(user.getId(), page + 1).isEmpty();
        model.addAttribute("isNextEmpty", isNextEmpty);
        model.addAttribute("transactionHistory", transactionHistory);
        model.addAttribute("user", user);
        model.addAttribute("transactionFilterDTO", new TransactionFilterDTO());
        model.addAttribute("page", page);
        return "admin/transaction-history";
    }

    @GetMapping("/admin/{username}/filtered-transactions")
    public String filterTransactions(@PathVariable String username,
                                     @Valid @ModelAttribute("transactionFilterDTO") TransactionFilterDTO transactionFilterDTO,
                                     @RequestParam(required = false, defaultValue = "1") Integer page,
                                     @RequestParam String startDate,
                                     @RequestParam String endDate,
                                     @RequestParam String searchRecipient,
                                     @RequestParam String direction,
                                     @RequestParam String sort,
                                     Model model) {
        User user = userService.getByUsername(username);
        int userId = user.getId();
        List<Transaction> filteredTransactions = transactionService.getFilteredTransactions(direction, startDate, endDate, searchRecipient, userId, page, sort);
        boolean isNextEmpty = transactionService.getFilteredTransactions(direction, startDate, endDate, searchRecipient, userId, page+1, sort).isEmpty();
        String[] tagsList = getStrings(transactionFilterDTO);
        model.addAttribute("isNextEmpty", isNextEmpty);
        model.addAttribute("transactionHistory", filteredTransactions);
        model.addAttribute("transactionFilterDTO", new TransactionFilterDTO());
        model.addAttribute("user", user);
        model.addAttribute("tagsList", tagsList);
        model.addAttribute("page", page);
        return "admin/filtered-transactions";
    }

    private String[] getStrings(@ModelAttribute("transactionFilterDTO") @Valid TransactionFilterDTO transactionFilterDTO) {
        String[] tagsList = new String[5];
        tagsList[0] = transactionFilterDTO.getStartDate();
        tagsList[1] = transactionFilterDTO.getEndDate();
        tagsList[2] = transactionFilterDTO.getSearchRecipient();
        tagsList[3] = transactionFilterDTO.getDirection();
        tagsList[4] = transactionFilterDTO.getSort();
        return tagsList;
    }
}
