package com.mikepaskual.delivery.customer.dto;

import com.mikepaskual.delivery.address.model.Address;
import com.mikepaskual.delivery.user.model.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CreateCustomerRequest {

    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Gender gender;
    private List<Address> addresses;
    private LocalDate birthday;
    private LocalDateTime createdAt;

    public CreateCustomerRequest() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        if (addresses == null) {
            this.addresses = new ArrayList<>();
        } else {
            this.addresses = new ArrayList<>(addresses);
        }
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

        private Long id;
        private String firstName;
        private String lastName;
        private String phone;
        private String email;
        private Gender gender;
        private List<Address> addresses;
        private LocalDate birthday;
        private LocalDateTime createdAt;

        Builder() {
            super();
        }

        @SuppressWarnings("unchecked")
        public S setBirthday(LocalDate birthday) {
            this.birthday = birthday;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setId(Long id) {
            this.id = id;
            return (S) this;
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
        public S setEmail(String email) {
            this.email = email;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setGender(Gender gender) {
            this.gender = gender;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setAddresses(List<Address> addresses) {
            this.addresses = addresses;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return (S) this;
        }

        public CreateCustomerRequest build() {
            CreateCustomerRequest request = new CreateCustomerRequest();
            request.setAddresses(this.addresses);
            request.setBirthday(this.birthday);
            request.setCreatedAt(this.createdAt);
            request.setEmail(this.email);
            request.setFirstName(this.firstName);
            request.setGender(this.gender);
            request.setId(this.id);
            request.setLastName(this.lastName);
            request.setPhone(this.phone);
            return request;
        }
    }
}
