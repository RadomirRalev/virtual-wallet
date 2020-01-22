package com.example.demo.controllers.mvccontrollers.transactions;

import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
