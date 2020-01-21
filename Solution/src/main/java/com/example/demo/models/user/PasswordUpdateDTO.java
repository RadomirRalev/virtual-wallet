package com.example.demo.models.user;

public class PasswordUpdateDTO {

    private String oldPassword;
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

    public PasswordUpdateDTO(String oldPassword, String newPassword, String newPasswordConfirmation) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.newPasswordConfirmation = newPasswordConfirmation;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
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
