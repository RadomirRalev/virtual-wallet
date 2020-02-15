package com.example.demo.controllers.mvccontrollers.user;

import com.example.demo.models.user.User;
import com.example.demo.services.TransactionService;
import com.example.demo.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


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
}
