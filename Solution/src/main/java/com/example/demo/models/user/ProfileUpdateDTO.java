package com.example.demo.models.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

public class ProfileUpdateDTO {

    @Email(regexp = "(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)"
            , message = "Please provide a valid email address.")
    private String email;
    @Pattern(regexp = "^[0][8][0-9]{8}+$", message = "Please provide a valid phone number.")
    private String phoneNumber;
    private byte[] picture;

    public ProfileUpdateDTO() {
    }

    public ProfileUpdateDTO(String email, String phoneNumber, byte[] picture) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.picture = picture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
