package com.example.demo.controllers.mvccontrollers.user;

import com.example.demo.models.user.User;
import com.example.demo.services.TransactionService;
import com.example.demo.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.example.demo.constants.SQLQueryConstants.DISABLE;
import static com.example.demo.constants.SQLQueryConstants.ENABLE;


@Controller
public class AdminPanelController {
    private UserService userService;
    private TransactionService transactionService;

    public AdminPanelController(UserService userService, TransactionService transactionService) {
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
                              @RequestParam(value = "criterium") String criterium, Model model) {
        List<User> searchResult;
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
        return "admin/home";
    }

    @PostMapping("/admin/{username}/un-blocked")
    public String AdminUnBlockedUser(@PathVariable("username") String username) {
        userService.setBlockedStatus(username, DISABLE);

        return "admin/home";
    }

    @PostMapping("/admin/{username}/disable")
    public String AdminDeleteUser(@PathVariable("username") String username) {
        userService.setStatusUser(username, DISABLE);
        return "admin/home";
    }

    @PostMapping("/admin/{username}/enable")
    public String AdminEnableUser(@PathVariable("username") String username) {
        userService.setStatusUser(username, ENABLE);
        return "admin/home";
    }
}
