package com.example.demo.controllers.mvccontrollers.user;

import com.example.demo.models.registration.RegistrationDTO;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class RegistrationController {
    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping("/registration")
    public String showRegistrationPage(Model model) {
        model.addAttribute("UserRegistration", new RegistrationDTO());
        return "registration";
    }
}
