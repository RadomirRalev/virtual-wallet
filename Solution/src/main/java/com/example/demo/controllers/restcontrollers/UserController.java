package com.example.demo.controllers.restcontrollers;

import com.example.demo.exceptions.*;
import com.example.demo.models.confirmIdentity.ConfirmIdentityRegistrationDTO;
import com.example.demo.models.user.*;
import com.example.demo.services.ConfirmIdentityService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.example.demo.constants.ExceptionConstants.IDENTITY_CONFIRM_REQUEST_PROCESSED;
import static com.example.demo.constants.ExceptionConstants.IDENTITY_CONFIRM_SUCCESS;
import static com.example.demo.constants.SQLQueryConstants.DISABLE;
import static com.example.demo.constants.SQLQueryConstants.ENABLE;
import static com.example.demo.helpers.UserHelper.currentPrincipalName;

@RestController
@RequestMapping("api/users")
public class UserController {

    private UserService userService;
    private ConfirmIdentityService confirmIdentityService;

    @Autowired
    public UserController(UserService userService, ConfirmIdentityService confirmIdentityService) {
        this.userService = userService;
        this.confirmIdentityService = confirmIdentityService;
    }

    @GetMapping
    public List<User> getUsers(int pages) {
        return userService.getUsers(pages);
    }

    @PostMapping
    public User create(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO) {
        try {
            return userService.createUser(userRegistrationDTO);
        } catch (DuplicateEntityException | InvalidOptionalFieldParameter | InvalidPasswordException |
                InvalidPictureFormat | IOException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable int id) {
        try {
            return userService.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/username/{username}")
    public User getByUsername(@PathVariable String username) {
        try {
            return userService.getByUsername(username);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/email/{email}")
    public User getByEmail(@PathVariable String email) {
        try {
            return userService.getByEmail(email);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/phone-number/{phoneNumber}")
    public User getByPhoneNumber(@PathVariable String phoneNumber) {
        try {
            return userService.getByPhoneNumber(phoneNumber);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/username/{username}")
    public User updateUser(@PathVariable String username, @RequestBody @Valid ProfileUpdateDTO profileUpdateDTO) {
        User userToUpdate = getByUsername(username);
        try {
            return userService.updateUser(userToUpdate, profileUpdateDTO);
        } catch (DuplicateEntityException | IOException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/username/password/{username}")
    public User updatePassword(@PathVariable String username, @RequestBody @Valid PasswordUpdateDTO passwordUpdateDTO) {
        User userToUpdate = getByUsername(username);
        try {
            return userService.changePassword(userToUpdate, passwordUpdateDTO);
        } catch (InvalidPasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/username/{username}")
    public void deleteUser(@PathVariable String username) {
        try {
            userService.setStatusUser(username, DISABLE);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/profile/information")
    public User editProfileInformation(@Valid @RequestBody ProfileUpdateDTO profileUpdateDTO) throws IOException {
        User user = userService.getByUsername(currentPrincipalName());
        return userService.updateUser(user, profileUpdateDTO);
    }

    @PostMapping("/profile/confirm-identity")
    public void confirmIdentity(@Valid @RequestBody ConfirmIdentityRegistrationDTO confirmIdentityRegistrationDTO) {
        User user = userService.getByUsername(currentPrincipalName());
        if (user.isConfirm_identity()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, IDENTITY_CONFIRM_SUCCESS);
        }
        if (confirmIdentityService.isUserHaveConfirmIdentityRequest(user.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, IDENTITY_CONFIRM_REQUEST_PROCESSED);
        }
        try {
            confirmIdentityService.createConfrimIdentity(confirmIdentityRegistrationDTO, currentPrincipalName());
        } catch (IOException | InvalidPictureFormat e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping("/admin/{username}/confirm-identity")
    public User AdminConfirmIdentity(@PathVariable("username") String username,
                                     @Valid @ModelAttribute("profileUpdateDTO") UserNamesDTO namesDTO) {
        try {
            User user = userService.getByUsername(username);
            userService.setStatusIdentity(username, ENABLE);
            return userService.updateNames(user, namesDTO, currentPrincipalName());
        } catch (EntityNotFoundException | DuplicateEntityException | InvalidPermission e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}
