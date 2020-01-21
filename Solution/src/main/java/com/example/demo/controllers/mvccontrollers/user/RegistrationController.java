package com.example.demo.controllers.mvccontrollers.user;


import com.example.demo.models.card.CardDTO;
import com.example.demo.models.user.UserRegistrationDTO;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping("/registration")
    public String showRegistrationPage(Model model) {
        model.addAttribute("UserRegistrationDTO", new UserRegistrationDTO());
        model.addAttribute("CardDTO", new CardDTO());
        return "registration";
    }

    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("UserRegistrationDTO", new UserRegistrationDTO());
        model.addAttribute("CardDTO", new CardDTO());
        return "test-registration";
    }
    @PostMapping("/test")
    public String test(@Valid @ModelAttribute("UserRegistration") UserRegistrationDTO userRegistrationDTO,
                               @Valid @ModelAttribute("CardDTO") CardDTO cardDTO) {

        userService.createUser(userRegistrationDTO,cardDTO);

        return "test-registration";
    }

}
