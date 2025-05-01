package com.mikepaskual.delivery.user.dto;

import com.mikepaskual.delivery.user.validation.PasswordsMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@PasswordsMatch(passwordField = "newPassword", confirmPasswordField = "verifyPassword")
public class UpdatePasswordRequest {

    @NotBlank(message = "{register.validation.password.notBlank}")
    private String currentPassword;
    @NotBlank(message = "{register.validation.password.notBlank}")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "{register.validation.password.pattern}")
    private String newPassword;
    @NotBlank(message = "{register.validation.repeatPassword.notBlank}")
    private String verifyPassword;

    public UpdatePasswordRequest() {
        super();
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    @SuppressWarnings("rawtypes")
    public static Builder builder() {
        return new Builder();
    }

    @SuppressWarnings("rawtypes")
    public static class Builder<S extends Builder> {

        private String currentPassword;
        private String newPassword;
        private String verifyPassword;

        Builder() {
            super();
        }

        @SuppressWarnings("unchecked")
        public S setNewPassword(String newPassword) {
            this.newPassword = newPassword;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setVerifyPassword(String verifyPassword) {
            this.verifyPassword = verifyPassword;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setCurrentPassword(String currentPassword) {
            this.currentPassword = currentPassword;
            return (S) this;
        }

        public UpdatePasswordRequest build() {
            UpdatePasswordRequest request = new UpdatePasswordRequest();
            request.setCurrentPassword(this.currentPassword);
            request.setNewPassword(this.newPassword);
            request.setVerifyPassword(this.verifyPassword);
            return request;
        }
    }
}
