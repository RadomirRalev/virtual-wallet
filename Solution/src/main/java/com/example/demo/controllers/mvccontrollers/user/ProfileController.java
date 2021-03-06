package com.example.demo.controllers.mvccontrollers.user;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InvalidPasswordException;
import com.example.demo.exceptions.InvalidPictureFormat;
import com.example.demo.models.transaction.Transaction;
import com.example.demo.models.transaction.TransactionFilterDTO;
import com.example.demo.models.user.PasswordUpdateDTO;
import com.example.demo.models.user.ProfileUpdateDTO;
import com.example.demo.models.user.User;
import com.example.demo.services.contracts.TransactionService;
import com.example.demo.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.example.demo.helpers.UserHelper.currentPrincipalName;

@Controller
public class ProfileController {
    private UserService userService;

    private TransactionService transactionService;


    @Autowired
    public ProfileController(UserService userService,
                             TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        User user = userService.getByUsername(currentPrincipalName());
        String availableSum = userService.getAvailableSum(user.getId());
        model.addAttribute("availableSum", availableSum);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile() {
        return "user/profile-edit";
    }

    @GetMapping("/profile/password")
    public String editProfilePassword(Model model) {
        PasswordUpdateDTO passwordUpdateDTO = new PasswordUpdateDTO();
        model.addAttribute("passwordUpdateDTO", passwordUpdateDTO);

        return "user/profile-edit-password";
    }

    @PostMapping("/profile/password")
    public String editProfilePassword(@Valid @ModelAttribute("passwordUpdateDTO") PasswordUpdateDTO passwordUpdateDTO,
                                      Model model) {
        try {
            User user = userService.getByUsername(currentPrincipalName());
            userService.changePassword(user, passwordUpdateDTO);
        } catch (InvalidPasswordException | EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "user/profile-edit-password";
        }
        return "messages/success-change-password";
    }

    @GetMapping("/profile/information")
    public String editProfileInformation(Model model) {
        ProfileUpdateDTO profileUpdateDTO = new ProfileUpdateDTO();
        model.addAttribute("profileUpdateDTO", profileUpdateDTO);

        return "user/profile-edit-profile";
    }

    @PostMapping("/profile/information")
    public String editProfileInformation(@Valid @ModelAttribute("profileUpdateDTO") ProfileUpdateDTO profileUpdateDTO,
                                         BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "user/profile-edit-profile";
        }

        try {
            User user = userService.getByUsername(currentPrincipalName());
            userService.updateUser(user, profileUpdateDTO);
        } catch (EntityNotFoundException | DuplicateEntityException | InvalidPictureFormat | IOException e) {
            model.addAttribute("error", e.getMessage());
            return "user/profile-edit-profile";
        }
        return "messages/success-change-information";
    }

    @GetMapping("/profile/{username}")
    public String account(@PathVariable("username") String username, Model model) {
        String currentUser = currentPrincipalName();

        User user = userService.getByUsername(currentPrincipalName());
        String availableSum = userService.getAvailableSum(user.getId());
        model.addAttribute("availableSum", availableSum);

        model.addAttribute("user", userService.getByUsername(username));
        model.addAttribute("currentuser", currentUser);
        return "/user/profile";
    }

    @GetMapping("/search")
    public String filterUsers(@RequestParam String text,
                              @RequestParam(value = "criterium") String criterium,
                              @RequestParam(required = false, defaultValue = "1") Integer page
            , Model model) {
        List<User> searchResult;
        switch (criterium) {
            case "username":
                searchResult = userService.searchByUsername(text, page);
                model.addAttribute("users", searchResult);
                boolean isNextEmpty = userService.searchByUsername(text, page + 1).isEmpty();
                model.addAttribute("isNextEmpty", isNextEmpty);
                break;
            case "phonenumber":
                searchResult = userService.searchByPhoneNumber(text, page);
                model.addAttribute("users", searchResult);
                isNextEmpty = userService.searchByPhoneNumber(text, page + 1).isEmpty();
                model.addAttribute("isNextEmpty", isNextEmpty);
                break;
            case "email":
                searchResult = userService.searchByEmail(text, page);
                model.addAttribute("users", searchResult);
                isNextEmpty = userService.searchByEmail(text, page + 1).isEmpty();
                model.addAttribute("isNextEmpty", isNextEmpty);
                break;
        }
        model.addAttribute("text", text);
        model.addAttribute("page", page);
        model.addAttribute("criterium", criterium);
        return "searchresults";
    }

    @GetMapping("/transactionhistory")
    public String getTransactionHistory(@RequestParam(required = false, defaultValue = "1") Integer page,
                                        Model model) {
        User user = userService.getByUsername(currentPrincipalName());
        List<Transaction> transactionHistory = transactionService.getTransactionsByUserId(user.getId(), page);
        boolean isNextEmpty = transactionService.getTransactionsByUserId(user.getId(), page + 1).isEmpty();
        model.addAttribute("isNextEmpty", isNextEmpty);
        model.addAttribute("transactionHistory", transactionHistory);
        model.addAttribute("user", user);
        model.addAttribute("transactionFilterDTO", new TransactionFilterDTO());
        model.addAttribute("page", page);
        return "user/transactionhistory";
    }

    @GetMapping("filteredtransactions")
    public String filterTransactions(Model model,
                                     @Valid @ModelAttribute("transactionFilterDTO") TransactionFilterDTO transactionFilterDTO,
                                     @RequestParam(required = false, defaultValue = "1") Integer page,
                                     @RequestParam String startDate,
                                     @RequestParam String endDate,
                                     @RequestParam String searchRecipient,
                                     @RequestParam String direction,
                                     @RequestParam String sort) {
        User user = userService.getByUsername(currentPrincipalName());
        int userId = user.getId();
        List<Transaction> filteredTransactions = transactionService.getFilteredTransactions(direction, startDate, endDate, searchRecipient, userId, page, sort);
        boolean isNextEmpty = transactionService.getFilteredTransactions(direction, startDate, endDate, searchRecipient, userId, page + 1, sort).isEmpty();
        String[] tagsList = getStrings(transactionFilterDTO);
        model.addAttribute("isNextEmpty", isNextEmpty);
        model.addAttribute("transactionHistory", filteredTransactions);
        model.addAttribute("transactionFilterDTO", new TransactionFilterDTO());
        model.addAttribute("tagsList", tagsList);
        model.addAttribute("page", page);
        return "user/filteredtransactions";
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
