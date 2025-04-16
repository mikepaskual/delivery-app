package com.mikepaskual.delivery.user.dto;

public class CreateUserRequest {

    private String username;
    private String password;
    private String verifyPassword;
    private String email;

    public CreateUserRequest() {
        super();
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder<S extends Builder> {

        private String username;
        private String password;
        private String verifyPassword;
        private String email;

        Builder() {
            super();
        }

        public S setPassword(String password) {
            this.password = password;
            return (S) this;
        }

        public S setVerifyPassword(String verifyPassword) {
            this.verifyPassword = verifyPassword;
            return (S) this;
        }

        public S setEmail(String email) {
            this.email = email;
            return (S) this;
        }

        public S setUsername(String username) {
            this.username = username;
            return (S) this;
        }

        public CreateUserRequest build() {
            CreateUserRequest request = new CreateUserRequest();
            request.setEmail(this.email);
            request.setPassword(this.password);
            request.setUsername(this.username);
            request.setVerifyPassword(this.verifyPassword);
            return request;
        }
    }
}
