package com.mikepaskual.delivery.user.dto;

import com.mikepaskual.delivery.user.validation.PasswordsMatch;
import com.mikepaskual.delivery.user.validation.UniqueEmail;
import com.mikepaskual.delivery.user.validation.UniqueUsername;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@PasswordsMatch
public class CreateUserRequest {

    @NotBlank(message = "{register.validation.username.notBlank}")
    @Size(min = 4, max = 20, message = "{register.validation.username.size}")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{register.validation.username.pattern}")
    @UniqueUsername
    private String username;
    @NotBlank(message = "{register.validation.password.notBlank}")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "{register.validation.password.pattern}")
    private String password;
    @NotBlank(message = "{register.validation.repeatPassword.notBlank}")
    private String verifyPassword;
    @NotBlank(message = "{register.validation.email.notBlank}")
    @Email(message = "{register.validation.email.pattern}")
    @UniqueEmail
    private String email;
    @NotEmpty(message = "{register.validation.role.notEmpty}")
    private Set<String> roles;
    private LocalDateTime createdAt;

    public CreateUserRequest() {
        roles = new HashSet<>();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
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
        private String verifyPassword;
        private String email;
        private Set<String> roles;
        private LocalDateTime createdAt;

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
        public S setVerifyPassword(String verifyPassword) {
            this.verifyPassword = verifyPassword;
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

        @SuppressWarnings("unchecked")
        public S setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return (S) this;
        }

        public CreateUserRequest build() {
            CreateUserRequest request = new CreateUserRequest();
            request.setCreatedAt(this.createdAt);
            request.setEmail(this.email);
            request.setPassword(this.password);
            request.setRoles(this.roles);
            request.setUsername(this.username);
            request.setVerifyPassword(this.verifyPassword);
            return request;
        }
    }
}
