package com.mikepaskual.delivery.address.model;

import com.mikepaskual.delivery.customer.model.Customer;
import com.mikepaskual.delivery.user.model.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "address_entity")
public class Address {

    @Id
    @GeneratedValue
    private Long id;
    private String street;
    private String postalCode;
    private String city;
    private String state;
    private String country;
    private boolean deleted;
    private boolean main;
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Address() {
        super();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @SuppressWarnings("rawtypes")
    public static Builder builder() {
        return new Builder();
    }

    @SuppressWarnings("rawtypes")
    public static class Builder<S extends Builder> {

        private Long id;
        private String street;
        private String postalCode;
        private String city;
        private String state;
        private String country;
        private boolean deleted;
        private boolean main;
        private LocalDateTime createdAt;
        private Customer customer;
        private User user;

        Builder() {
            super();
        }

        @SuppressWarnings("unchecked")
        public S setCustomer(Customer customer) {
            this.customer = customer;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setUser(User user) {
            this.user = user;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setMain(boolean main) {
            this.main = main;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setDeleted(boolean deleted) {
            this.deleted = deleted;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setState(String state) {
            this.state = state;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setStreet(String street) {
            this.street = street;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setId(Long id) {
            this.id = id;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setCity(String city) {
            this.city = city;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setCountry(String country) {
            this.country = country;
            return (S) this;
        }

        public Address build() {
            Address address = new Address();
            address.setCity(this.city);
            address.setCountry(this.country);
            address.setCreatedAt(this.createdAt);
            address.setCustomer(this.customer);
            address.setDeleted(this.deleted);
            address.setId(this.id);
            address.setMain(this.main);
            address.setPostalCode(this.postalCode);
            address.setState(this.state);
            address.setStreet(this.street);
            address.setUser(this.user);
            return address;
        }
    }
}
