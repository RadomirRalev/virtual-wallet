package com.example.demo.models.registration;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

public class RegistrationDTO {
    @Length(min = 4, max = 30)
    @Pattern(regexp = "^[\\w-]+$",
            message = "Username may only contain alpha-numeric characters, underscores, and dashes.")
    private String username;

    private int enabled;
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
    //TODO regex !!!
    private String phoneNumber;
    private byte[] picture;

    private String number;
    private String expirationDate;
    private int securityCode;

    public RegistrationDTO() {
    }

    public RegistrationDTO(String username, int enabled, String email, String password,
                           String passwordConfirmation, String phoneNumber, byte[] picture,
                           String number, String expirationDate, int securityCode) {
        this.username = username;
        this.enabled = enabled;
        this.email = email;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.phoneNumber = phoneNumber;
        this.picture = picture;
        this.number = number;
        this.expirationDate = expirationDate;
        this.securityCode = securityCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
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

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(int securityCode) {
        this.securityCode = securityCode;
    }
}
