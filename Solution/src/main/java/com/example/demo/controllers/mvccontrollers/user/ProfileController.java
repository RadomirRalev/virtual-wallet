package com.example.demo.controllers.mvccontrollers.user;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InvalidPasswordException;
import com.example.demo.models.transaction.DateFilterDTO;
import com.example.demo.models.transaction.Transaction;
import com.example.demo.models.user.ConfirmIdentityRegistrationDTO;
import com.example.demo.models.user.PasswordUpdateDTO;
import com.example.demo.models.user.ProfileUpdateDTO;
import com.example.demo.models.user.User;
import com.example.demo.repositories.WalletRepository;
import com.example.demo.services.ConfirmIdentityService;
import com.example.demo.services.TransactionService;
import com.example.demo.services.UserService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.demo.helpers.UserHelper.currentPrincipalName;

@Controller
public class ProfileController {
    private UserService userService;
    private ConfirmIdentityService confirmIdentityService;
    private WalletRepository walletRepository;
    private TransactionService transactionService;


    @Autowired
    public ProfileController(UserService userService,ConfirmIdentityService confirmIdentityService,
                             WalletRepository walletRepository, TransactionService transactionService) {
        this.userService = userService;
        this.confirmIdentityService = confirmIdentityService;
        this.walletRepository = walletRepository;
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
    public String editProfile(Model model) {
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
                                Model model) {
        try {
            User user = userService.getByUsername(currentPrincipalName());
            userService.updateUser(user, profileUpdateDTO);
        } catch (EntityNotFoundException | IOException e) {
            model.addAttribute("error", e.getMessage());
            return "user/profile-edit-profile";
        }
        return "messages/success-change-information";
    }

    @GetMapping("/profile/confirm-identity")
    public String confirmIdentity(Model model) {
        ConfirmIdentityRegistrationDTO confirmIdentityRegistrationDTO = new ConfirmIdentityRegistrationDTO();
        model.addAttribute("confirmIdentityRegistrationDTO", confirmIdentityRegistrationDTO);

        return "user/confirm-identity";
    }


    //TODO
    @PostMapping("/profile/confirm-identity")
    public String confirmIdentity(@Valid @ModelAttribute("confirmIdentityRegistrationDTO")
                                          ConfirmIdentityRegistrationDTO confirmIdentityRegistrationDTO,
                                  Model model) {
        User user = userService.getByUsername(currentPrincipalName());
        String extension = FilenameUtils.getExtension(confirmIdentityRegistrationDTO.getFront_picture().getOriginalFilename());
        String extension1 = FilenameUtils.getExtension(confirmIdentityRegistrationDTO.getRear_picture().getOriginalFilename());
        String extension2 = FilenameUtils.getExtension(confirmIdentityRegistrationDTO.getSelfie().getOriginalFilename());

        try {
            confirmIdentityService.createConfrimIdentity(confirmIdentityRegistrationDTO,user.getId() );
        } catch (IOException  e) {
            model.addAttribute("error", e.getMessage());
            return "user/confirm-identity";
        }
        return "messages/success-confirm-identity";
    }

    @GetMapping("/profile/{username}")
    public String account(@PathVariable("username") String username, Model model) {
        String currentUser = currentPrincipalName();
        try {
            User user = userService.getByUsername(currentPrincipalName());
            String availableSum = userService.getAvailableSum(user.getId());
            model.addAttribute("availableSum", availableSum);
        } catch (EntityNotFoundException e) {

        }
        model.addAttribute("user", userService.getByUsername(username));
        model.addAttribute("currentuser", currentUser);
        return "/user/profile";
    }

    @GetMapping("/search")
    public String filterUsers(@RequestParam String text,
                              @RequestParam(value = "criterium") String criterium
            , Model model) {
        List<User> searchResult;
        switch(criterium) {
            case "username" :
                searchResult = userService.searchByUsername(text);
                model.addAttribute("users", searchResult);
                break;
            case "phonenumber" :
                searchResult = userService.searchByPhoneNumber(text);
                model.addAttribute("users", searchResult);
                break;
            case "email" :
                searchResult = userService.searchByEmail(text);
                model.addAttribute("users", searchResult);
                break;
        }
        return "searchresults";
    }

    @GetMapping("/transactionhistory")
    public String getTransactionHistory(Model model) {
        User user = userService.getByUsername(currentPrincipalName());
        List<Transaction> transactionHistory = transactionService.getTransactionsByUserId(user.getId());
        model.addAttribute("transactionHistory", transactionHistory);
        model.addAttribute("user", user);
        model.addAttribute("dateFilterDTO", new DateFilterDTO());
        return "transactionhistory";
    }

    @GetMapping("/transactionshistory/filterbydate")
    public String getTransactionsByDate(Model model,
                                        @ModelAttribute("dateFilterDTO") DateFilterDTO dateFilterDTO) {
        User user = userService.getByUsername(currentPrincipalName());
        String start = dateFilterDTO.getStartDate();
        String end = dateFilterDTO.getEndDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.parse(start, formatter);
        LocalDate endDate = LocalDate.parse(end, formatter);
        List<Transaction> filteredTransactions = transactionService.getTransactionsByDate(startDate, endDate, user.getId());
        model.addAttribute("dateFilterDTO", dateFilterDTO);
        model.addAttribute("transactionHistory", filteredTransactions);
        return "transactionhistory";
    }
}
