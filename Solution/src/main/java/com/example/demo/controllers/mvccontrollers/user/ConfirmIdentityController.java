package com.example.demo.controllers.mvccontrollers.user;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InvalidPictureFormat;
import com.example.demo.models.confirmIdentity.ConfirmIdentity;
import com.example.demo.models.confirmIdentity.ConfirmIdentityRegistrationDTO;
import com.example.demo.models.user.User;
import com.example.demo.models.user.UserNamesDTO;
import com.example.demo.services.contracts.ConfirmIdentityService;
import com.example.demo.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.IOException;

import static com.example.demo.constants.ExceptionConstants.IDENTITY_CONFIRM_REQUEST_PROCESSED;
import static com.example.demo.constants.ExceptionConstants.IDENTITY_CONFIRM_SUCCESS;
import static com.example.demo.constants.SQLQueryConstants.DISABLE;
import static com.example.demo.constants.SQLQueryConstants.ENABLE;
import static com.example.demo.helpers.UserHelper.currentPrincipalName;

@Controller
public class ConfirmIdentityController {

    private UserService userService;
    private ConfirmIdentityService confirmIdentityService;

    @Autowired
    public ConfirmIdentityController(UserService userService, ConfirmIdentityService confirmIdentityService) {
        this.userService = userService;
        this.confirmIdentityService = confirmIdentityService;
    }

    @GetMapping("/confirm-identity")
    public String confirmIdentity(Model model) {
        model.addAttribute("confirmIdentityRegistrationDTO", new ConfirmIdentityRegistrationDTO());

        User user = userService.getByUsername(currentPrincipalName());
        if (user.isConfirm_identity()) {
            model.addAttribute("error", IDENTITY_CONFIRM_SUCCESS);
            return "error";
        }

        if (confirmIdentityService.isUserHaveConfirmIdentityRequest(user.getId())) {
            model.addAttribute("error", IDENTITY_CONFIRM_REQUEST_PROCESSED);
            return "error";
        }

        return "user/confirm-identity";
    }

    @PostMapping("/confirm-identity")
    public String confirmIdentity(@Valid @ModelAttribute("confirmIdentityRegistrationDTO")
                                          ConfirmIdentityRegistrationDTO confirmIdentityRegistrationDTO, Model model) {
        try {
            confirmIdentityService.createConfrimIdentity(confirmIdentityRegistrationDTO, currentPrincipalName());
        } catch (IOException | InvalidPictureFormat e) {
            model.addAttribute("error", e.getMessage());
            return "user/confirm-identity";
        }
        return "messages/success-confirm-identity-request";
    }

    @GetMapping("/admin/{username}/confirm-identity")
    public String AdminConfirmIdentity(@PathVariable("username") String username, Model model) {
        model.addAttribute("namesDTO", new UserNamesDTO());
        User user = userService.getByUsername(username);
        model.addAttribute("user", user);

        if (userService.isIdentityConfirm(username)) {
            return "messages/user-already-confirm-identity";
        }
        try {
            ConfirmIdentity confirmIdentity = confirmIdentityService.getByUserIdRequestForConfirm(user.getId());
            model.addAttribute("confirmIdentity", confirmIdentity);
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
        return "admin/confirm-identity";
    }

    @PostMapping("/admin/{username}/confirm-identity")
    public String AdminConfirmIdentity(@PathVariable("username") String username,
                                       @Valid @ModelAttribute("profileUpdateDTO") UserNamesDTO namesDTO,
                                       BindingResult bindingResult, Model model) {
        User userToUpdate = userService.getByUsername(username);
        ConfirmIdentity confirmIdentity = confirmIdentityService.getByUserIdRequestForConfirm(userToUpdate.getId());
        model.addAttribute("confirmIdentity", confirmIdentity);
        model.addAttribute("user", userToUpdate);

        if (bindingResult.hasErrors()) {
            return "admin/confirm-identity";
        }

        confirmIdentityService.setStatus(userToUpdate.getId(), DISABLE);
        userService.updateNames(userToUpdate, namesDTO, currentPrincipalName());
        userService.setStatusIdentity(username, ENABLE);
        return "messages/success-confirm-identity";
    }

    @PostMapping("/admin/{username}/denied-identity")
    public String AdminDeniedIdentity(@PathVariable("username") String username) {
        User userToUpdate = userService.getByUsername(username);

        confirmIdentityService.setStatus(userToUpdate.getId(), DISABLE);
        return "messages/success-delete-identity";
    }


}
