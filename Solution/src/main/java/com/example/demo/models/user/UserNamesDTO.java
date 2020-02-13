package com.example.demo.models.user;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

public class UserNamesDTO {
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name field may contain only letters.")
    @Length(min = 3, message = "Minimum 3 symbols required.")
    private String firstName;
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name field may contain only letters.")
    @Length(min = 3, message = "Minimum 3 symbols required.")
    private String lastName;

    public UserNamesDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
