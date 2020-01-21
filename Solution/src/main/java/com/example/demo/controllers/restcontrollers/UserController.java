package com.example.demo.controllers.restcontrollers;

import com.example.demo.exceptions.DuplicateEntityException;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.InvalidOptionalFieldParameter;
import com.example.demo.exceptions.InvalidPasswordException;
import com.example.demo.models.card.CardDTO;
import com.example.demo.models.user.PasswordUpdateDTO;
import com.example.demo.models.user.ProfileUpdateDTO;
import com.example.demo.models.user.User;
import com.example.demo.models.user.UserRegistrationDTO;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static com.example.demo.constants.SQLQueryConstants.DISABLE;

@RestController
@RequestMapping("api/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public User create(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO, CardDTO cardDTO) {
        try {
//            return userService.createUser(userRegistrationDTO, cardDTO);
            return userService.createUser(userRegistrationDTO);
        } catch (DuplicateEntityException | InvalidOptionalFieldParameter | InvalidPasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
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
        } catch (DuplicateEntityException e) {
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
        }
        catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
