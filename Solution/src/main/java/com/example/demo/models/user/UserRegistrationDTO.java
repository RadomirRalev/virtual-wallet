package com.example.demo.models.user;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

public class UserRegistrationDTO {
    @Length(min = 4, max = 30)
    @Pattern(regexp = "^[\\w-]+$",
            message = "Username may only contain alpha-numeric characters, underscores and dashes.")
    private String username;
    @Email(regexp = "(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)"
            , message = "Please provide a valid email address.")
    private String email;
    //    @Length(min = 8, max = 16)
//    @Pattern(regexp = "^(?=.*[0-9]+.*)(?=.*[a-z]+.*)(?=.*[A-Z]+.*)+[!-~]+$",
//            message = "Password may contain little letters, big letters and numbers")
    private String password;
    //    @Length(min = 8, max = 16)
//    @Pattern(regexp = "^(?=.*[0-9]+.*)(?=.*[a-z]+.*)(?=.*[A-Z]+.*)+[!-~]+$",
//            message = "Password may contain little letters, big letters and numbers")
    private String passwordConfirmation;
    @Pattern(regexp = "^[0][8][0-9]{8}+$", message = "Please provide a valid phone number. Format 08XXXXXXXX")
    private String phoneNumber;
    private int walletId;
    private MultipartFile file;


    public UserRegistrationDTO() {
    }

    public UserRegistrationDTO(String username, String email, String password,
                               String passwordConfirmation, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.phoneNumber = phoneNumber;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getWalletId() {
        return walletId;
    }

    public void setWalletId(int walletId) {
        this.walletId = walletId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }


}
