package com.example.demo.controllers.mvccontrollers.user;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InvalidPasswordException;
import com.example.demo.models.user.*;
import com.example.demo.models.wallet.Wallet;
import com.example.demo.services.ConfirmIdentityService;
import com.example.demo.services.UserService;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

import java.io.FilenameFilter;
import java.io.IOException;

import static com.example.demo.helpers.UserHelper.currentPrincipalName;

@Controller
public class ProfileController {
    private UserService userService;
    private ConfirmIdentityService confirmIdentityService;

    @Autowired
    public ProfileController(UserService userService, ConfirmIdentityService confirmIdentityService) {
        this.userService = userService;
        this.confirmIdentityService = confirmIdentityService;

    }

    @GetMapping("/profile")
    public String profile(Model model) {
        User user = userService.getByUsername(currentPrincipalName());
        Wallet wallet = user.getWallets().get(0);
        model.addAttribute("user", user);
        model.addAttribute("wallet", wallet);
        return "user/profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile(Model model) {
        return "user/profile-edit";
    }

    @GetMapping("/profile/password")
    public String editProfilePassword(Model model) {
        PasswordUpdateDTO passwordUpdateDTO = new PasswordUpdateDTO();
        model.addAttribute("passwordUpdateDTO", passwordUpdateDTO);

        return "user/profile-edit-password";
    }

    @PostMapping("/profile/password")
    public String editProfilePassword(@Valid @ModelAttribute("passwordUpdateDTO") PasswordUpdateDTO passwordUpdateDTO,
                                      Model model) {
        try {
            User user = userService.getByUsername(currentPrincipalName());
            userService.changePassword(user, passwordUpdateDTO);
        } catch (InvalidPasswordException | EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "user/profile-edit-password";
        }
        return "messages/success-change-password";
    }

    @GetMapping("/profile/information")
    public String editProfileInformation(Model model) {
        ProfileUpdateDTO profileUpdateDTO = new ProfileUpdateDTO();
        model.addAttribute("profileUpdateDTO", profileUpdateDTO);

        return "user/profile-edit-profile";
    }

    @PostMapping("/profile/information")
    public String editProfileInformation(@Valid @ModelAttribute("profileUpdateDTO") ProfileUpdateDTO profileUpdateDTO,
                                         Model model) {
        try {
            User user = userService.getByUsername(currentPrincipalName());
            userService.updateUser(user, profileUpdateDTO);
        } catch (EntityNotFoundException | IOException e) {
            model.addAttribute("error", e.getMessage());
            return "user/profile-edit-profile";
        }
        return "messages/success-change-information";
    }

    @GetMapping("/profile/confirm-identity")
    public String confirmIdentity(Model model) {
        ConfirmIdentityRegistrationDTO confirmIdentityRegistrationDTO = new ConfirmIdentityRegistrationDTO();
        model.addAttribute("confirmIdentityRegistrationDTO", confirmIdentityRegistrationDTO);

        return "user/confirm-identity";
    }

    @PostMapping("/profile/confirm-identity")
    public String confirmIdentity(@Valid @ModelAttribute("confirmIdentityRegistrationDTO")
                                          ConfirmIdentityRegistrationDTO confirmIdentityRegistrationDTO,
                                  Model model) {
        User user = userService.getByUsername(currentPrincipalName());
        String extension = FilenameUtils.getExtension(confirmIdentityRegistrationDTO.getFront_picture().getOriginalFilename());
        String extension1 = FilenameUtils.getExtension(confirmIdentityRegistrationDTO.getRear_picture().getOriginalFilename());
        String extension2 = FilenameUtils.getExtension(confirmIdentityRegistrationDTO.getSelfie().getOriginalFilename());
        long kur = confirmIdentityRegistrationDTO.getSelfie().getSize();

        try {
            confirmIdentityService.createConfrimIdentity(confirmIdentityRegistrationDTO,user.getId() );
        } catch (IOException  e) {
            model.addAttribute("error", e.getMessage());
            return "user/confirm-identity";
        }
        return "messages/success-confirm-identity";
    }
}
