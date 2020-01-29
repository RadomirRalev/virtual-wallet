package com.example.demo.controllers.mvccontrollers.user;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InvalidPasswordException;
import com.example.demo.models.user.PasswordUpdateDTO;
import com.example.demo.models.user.ProfileUpdateDTO;
import com.example.demo.models.user.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.Valid;

import java.io.IOException;

import static com.example.demo.helpers.UserHelper.currentPrincipalName;

@Controller
public class ProfileController {
    private UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        User user = userService.getByUsername(currentPrincipalName());
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile(Model model) {
        PasswordUpdateDTO passwordUpdateDTO = new PasswordUpdateDTO();
        ProfileUpdateDTO profileUpdateDTO = new ProfileUpdateDTO();
        model.addAttribute("passwordUpdateDTO", passwordUpdateDTO);
        model.addAttribute("profileUpdateDTO", profileUpdateDTO);

        return "profile-edit";
    }

    @PostMapping("/profile/edit")
    public String updatePassword(@Valid @ModelAttribute("passwordUpdateDTO") PasswordUpdateDTO passwordUpdateDTO,
                                 Model model) {
        try {
            User user = userService.getByUsername(currentPrincipalName());
            userService.changePassword(user, passwordUpdateDTO);
        } catch (InvalidPasswordException | EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "profile-edit";
        }
        return "profile-edit";
    }

//    @PostMapping("/profile/edit")
//    public String updateProfile(@Valid @ModelAttribute("profileUpdateDTO") ProfileUpdateDTO profileUpdateDTO,
//                                 Model model) {
//        try {
//            User user = userService.getByUsername(currentPrincipalName());
//            userService.updateUser(user, profileUpdateDTO);
//        } catch (EntityNotFoundException | IOException e) {
//            model.addAttribute("error", e.getMessage());
//            return "profile-edit";
//        }
//        return "profile-edit";
//    }

}
