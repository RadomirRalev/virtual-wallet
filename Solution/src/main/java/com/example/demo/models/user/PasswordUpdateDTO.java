package com.example.demo.models.user;

public class PasswordUpdateDTO {

    private String currentPassword;
    //    @Length(min = 8, max = 16)
//    @Pattern(regexp = "^(?=.*[0-9]+.*)(?=.*[a-z]+.*)(?=.*[A-Z]+.*)+[!-~]+$",
//            message = "Password may contain little letters, big letters and numbers")
    private String newPassword;
    //    @Length(min = 8, max = 16)
//    @Pattern(regexp = "^(?=.*[0-9]+.*)(?=.*[a-z]+.*)(?=.*[A-Z]+.*)+[!-~]+$",
//            message = "Password may contain little letters, big letters and numbers")
    private String newPasswordConfirmation;

    public PasswordUpdateDTO() {
    }

    public PasswordUpdateDTO(String currentPassword, String newPassword, String newPasswordConfirmation) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.newPasswordConfirmation = newPasswordConfirmation;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirmation() {
        return newPasswordConfirmation;
    }

    public void setNewPasswordConfirmation(String newPasswordConfirmation) {
        this.newPasswordConfirmation = newPasswordConfirmation;
    }
}
