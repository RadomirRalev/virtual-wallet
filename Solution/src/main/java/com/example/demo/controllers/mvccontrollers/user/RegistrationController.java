package com.example.demo.controllers.mvccontrollers.user;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InvalidOptionalFieldParameter;
import com.example.demo.exceptions.InvalidPasswordException;
import com.example.demo.models.user.User;
import com.example.demo.models.user.UserRegistrationDTO;
import com.example.demo.models.verificationToken.VerificationToken;
import com.example.demo.services.UserService;
import com.example.demo.services.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.example.demo.constants.ExceptionConstants.INVALID_TOKEN;

@Controller
public class RegistrationController {
    private UserService userService;
    private VerificationTokenService verificationTokenService;

    @Autowired
    public RegistrationController(UserService userService,
                                  VerificationTokenService verificationTokenService) {
        this.userService = userService;
        this.verificationTokenService = verificationTokenService;
    }

    @GetMapping("/registration")
    public String showRegistrationPage(Model model) {
        model.addAttribute("UserRegistrationDTO", new UserRegistrationDTO());
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(@Valid @ModelAttribute("UserRegistrationDTO") UserRegistrationDTO userRegistrationDTO,
                             BindingResult bindingResult,
                             Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        try {
             userService.createUser(userRegistrationDTO);
        } catch (InvalidOptionalFieldParameter | DuplicateEntityException | InvalidPasswordException | IOException e) {
            model.addAttribute("error", e.getMessage());
            return "registration";
        }

        return "messages/register-account";
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public String confirmUserAccount(@RequestParam("token")String confirmationToken,
                                     Model model) {

        try {
            VerificationToken token = verificationTokenService.getByToken(confirmationToken);
            User user = userService.getById(token.getUser_id());
            userService.setStatusUser(user.getUsername(),true);
        }
        catch (EntityNotFoundException e){
            model.addAttribute("error", INVALID_TOKEN);
            return "error";
        }

        return "messages/confirm-account";
    }

//    @GetMapping("/userslist")
//    public String getUsersList(@RequestParam(required = false, defaultValue = "1") Integer page,
//                               Model model) {
//        List<User> users = userService.getUsersPaginatedHibernate(page);
//        List<Integer> pages = userService.getPages();
//        model.addAttribute("users", users);
//        model.addAttribute("pages", pages);
//        return "user/userslist";
//    }

    @GetMapping("/userslist")
    public String getUsersList(@RequestParam(required = false, defaultValue = "1") Integer page,
                               Model model) {
        List<User> users = userService.getUsers(page);
        model.addAttribute("users", users);
        model.addAttribute("page", page);
        return "user/userslist";
    }
}
