package com.mikepaskual.delivery.user.dto;

import com.mikepaskual.delivery.user.validation.PasswordsMatch;
import com.mikepaskual.delivery.user.validation.UniqueEmail;
import com.mikepaskual.delivery.user.validation.UniqueUsername;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@PasswordsMatch(passwordField = "password", confirmPasswordField = "repeatPassword")
public class CreateUserRequest {

    @NotBlank(message = "{register.validation.username.notBlank}")
    @Size(min = 4, max = 20, message = "{register.validation.username.size}")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{register.validation.username.pattern}")
    @UniqueUsername
    private String username;
    @NotBlank(message = "{password.validation.notBlank}")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "{password.validation.pattern}")
    private String password;
    @NotBlank(message = "{password.validation.notBlank}")
    private String repeatPassword;
    @NotBlank(message = "{register.validation.email.notBlank}")
    @Email(message = "{register.validation.email.pattern}")
    @UniqueEmail
    private String email;
    @NotEmpty(message = "{register.validation.role.notEmpty}")
    private Set<String> roles;

    public CreateUserRequest() {
        roles = new HashSet<>();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        if (roles == null) {
            this.roles = new HashSet<>();
        } else {
            this.roles = new HashSet<>(roles);
        }
    }

    @SuppressWarnings("rawtypes")
    public static Builder builder() {
        return new Builder();
    }

    @SuppressWarnings("rawtypes")
    public static class Builder<S extends Builder> {

        private String username;
        private String password;
        private String repeatPassword;
        private String email;
        private Set<String> roles;

        Builder() {
            super();
        }

        @SuppressWarnings("unchecked")
        public S setRoles(Set<String> roles) {
            this.roles = roles;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setPassword(String password) {
            this.password = password;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setRepeatPassword(String repeatPassword) {
            this.repeatPassword = repeatPassword;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setEmail(String email) {
            this.email = email;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setUsername(String username) {
            this.username = username;
            return (S) this;
        }

        public CreateUserRequest build() {
            CreateUserRequest request = new CreateUserRequest();
            request.setEmail(this.email);
            request.setPassword(this.password);
            request.setRepeatPassword(this.repeatPassword);
            request.setRoles(this.roles);
            request.setUsername(this.username);
            return request;
        }
    }
}
