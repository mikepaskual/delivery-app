package com.mikepaskual.delivery.user.dto;

import com.mikepaskual.delivery.user.validation.PasswordsMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@PasswordsMatch(passwordField = "newPassword", confirmPasswordField = "repeatPassword")
public class UpdatePasswordRequest {

    @NotBlank(message = "{password.validation.notBlank}")
    private String currentPassword;
    @NotBlank(message = "{password.validation.notBlank}")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "{password.validation.pattern}")
    private String newPassword;
    @NotBlank(message = "{password.validation.notBlank}")
    private String repeatPassword;

    public UpdatePasswordRequest() {
        super();
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
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
        private String repeatPassword;

        Builder() {
            super();
        }

        @SuppressWarnings("unchecked")
        public S setNewPassword(String newPassword) {
            this.newPassword = newPassword;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setRepeatPassword(String repeatPassword) {
            this.repeatPassword = repeatPassword;
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
            request.setRepeatPassword(this.repeatPassword);
            return request;
        }
    }
}
