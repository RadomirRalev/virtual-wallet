package com.example.demo.controllers.mvccontrollers.user;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InvalidPictureFormat;
import com.example.demo.models.confirmIdentity.ConfirmIdentity;
import com.example.demo.models.confirmIdentity.ConfirmIdentityRegistrationDTO;
import com.example.demo.models.user.User;
import com.example.demo.services.ConfirmIdentityService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.IOException;

import static com.example.demo.constants.ExceptionConstants.IDENTITY_CONFIRM_REQUEST_PROCESSED;
import static com.example.demo.constants.ExceptionConstants.IDENTITY_CONFIRM_SUCCESS;
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

    @GetMapping("/profile/confirm-identity")
    public String confirmIdentity(Model model) {
        ConfirmIdentityRegistrationDTO confirmIdentityRegistrationDTO = new ConfirmIdentityRegistrationDTO();
        model.addAttribute("confirmIdentityRegistrationDTO", confirmIdentityRegistrationDTO);

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

    @PostMapping("/profile/confirm-identity")
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
    public String AdminConfirmIdentity(@PathVariable("username") String username) {

        userService.setStatusIdentity(username, ENABLE);
        return "messages/success-confirm-identity";
    }
}
