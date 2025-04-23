package com.mikepaskual.delivery.user.dto;

import java.time.LocalDateTime;

public class CreateUserRequest {

    private String username;
    private String password;
    private String verifyPassword;
    private String email;
    private LocalDateTime createdAt;

    public CreateUserRequest() {
        super();
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
        private LocalDateTime createdAt;

        Builder() {
            super();
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
            request.setUsername(this.username);
            request.setVerifyPassword(this.verifyPassword);
            return request;
        }
    }
}
