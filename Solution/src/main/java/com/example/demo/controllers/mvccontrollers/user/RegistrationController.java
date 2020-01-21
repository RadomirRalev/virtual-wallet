package com.example.demo.controllers.mvccontrollers.user;


import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.InvalidOptionalFieldParameter;
import com.example.demo.models.card.CardDTO;
import com.example.demo.models.user.User;
import com.example.demo.models.user.UserRegistrationDTO;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

import static com.example.demo.constants.ExceptionConstants.PASSWORD_DO_NOT_MATCH;

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
        return "registration";
    }

    @PostMapping("registration")
    public String createBeer(@RequestParam("file") MultipartFile file,
                             @Valid @ModelAttribute("UserRegistrationDTO") UserRegistrationDTO userRegistrationDTO,
                             Model model, BindingResult bindingResult) throws IOException {
        model.addAttribute("file", file);
        userRegistrationDTO.setPicture(file.getBytes());
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        if (!userRegistrationDTO.getPassword().equals(userRegistrationDTO.getPasswordConfirmation())) {
            model.addAttribute("error", PASSWORD_DO_NOT_MATCH);
            return "registration";
        }

        try {
            userService.createUser(userRegistrationDTO);
        } catch (InvalidOptionalFieldParameter | DuplicateEntityException e) {
            model.addAttribute("error", e.getMessage());
            return "registration";
        }
        return "redirect:/login";
    }


//    @GetMapping("/test")
//    public String test(Model model) {
//        model.addAttribute("UserRegistrationDTO", new UserRegistrationDTO());
//        model.addAttribute("CardDTO", new CardDTO());
//        return "test-registration";
//    }
//
//    @PostMapping("/test")
//    public String test(@Valid @ModelAttribute("UserRegistration") UserRegistrationDTO userRegistrationDTO,
//                       @Valid @ModelAttribute("CardDTO") CardDTO cardDTO) {
//
//        userService.createUser(userRegistrationDTO, cardDTO);
//
//        return "test-registration";
//    }

}
