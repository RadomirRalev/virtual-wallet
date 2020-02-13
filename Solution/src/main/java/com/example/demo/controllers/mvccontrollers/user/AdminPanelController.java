package com.example.demo.controllers.mvccontrollers.user;

import com.example.demo.services.TransactionService;
import com.example.demo.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AdminPanelController {
    private UserService userService;
    private TransactionService transactionService;

    public AdminPanelController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping("/admin")
    public String profile() {
        return "admin/home";
    }
}
