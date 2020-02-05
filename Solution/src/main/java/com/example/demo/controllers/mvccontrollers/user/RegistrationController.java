package com.example.demo.controllers.mvccontrollers.user;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.InvalidOptionalFieldParameter;
import com.example.demo.exceptions.InvalidPasswordException;
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
import java.util.List;


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

    @PostMapping("/registration")
    public String createUser(@Valid @ModelAttribute("UserRegistrationDTO") UserRegistrationDTO userRegistrationDTO,
                              BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        try {
            userService.createUser(userRegistrationDTO);
        } catch (InvalidOptionalFieldParameter | DuplicateEntityException | InvalidPasswordException | IOException e) {
            model.addAttribute("error", e.getMessage());
            return "registration";
        }
        return "successregistration";
    }

    @GetMapping("/userslist")
    public String getUsersList(@RequestParam(required = false, defaultValue = "1") Integer page,
                               Model model) {
        List<User> users = userService.getUsersPaginatedHibernate(page);
        List<Integer> pages = userService.getPages();
        model.addAttribute("users", users);
        model.addAttribute("pages", pages);
        return "userslist";
    }
}
