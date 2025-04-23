package com.mikepaskual.delivery.user.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class UpdateUserRequest {

    private String firstName;
    private String lastName;
    private String phone;
    private String gender;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;

    public UpdateUserRequest() {
        super();
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @SuppressWarnings("rawtypes")
    public static Builder builder() {
        return new Builder();
    }

    @SuppressWarnings("rawtypes")
    public static class Builder<S extends Builder> {

        private String firstName;
        private String lastName;
        private String phone;
        private String gender;
        private LocalDate birthday;

        Builder() {
            super();
        }

        @SuppressWarnings("unchecked")
        public S setFirstName(String firstName) {
            this.firstName = firstName;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setLastName(String lastName) {
            this.lastName = lastName;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setPhone(String phone) {
            this.phone = phone;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setGender(String gender) {
            this.gender = gender;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setBirthday(LocalDate birthday) {
            this.birthday = birthday;
            return (S) this;
        }

        public UpdateUserRequest build() {
            UpdateUserRequest request = new UpdateUserRequest();
            request.setBirthday(this.birthday);
            request.setFirstName(this.firstName);
            request.setGender(this.gender);
            request.setLastName(this.lastName);
            request.setPhone(this.phone);
            return request;
        }
    }
}
