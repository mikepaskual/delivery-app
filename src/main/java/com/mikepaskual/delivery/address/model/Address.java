package com.mikepaskual.delivery.address.model;

import com.mikepaskual.delivery.customer.model.Customer;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "address_entity")
public class Address {

    @Id
    @GeneratedValue
    private Long id;
    private String streetNumber;
    private String streetName;
    private String streetSuffix;
    private String postalCode;
    private String city;
    private String state;
    private String country;
    private boolean hidden;
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Address() {
        super();
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

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
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

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetSuffix() {
        return streetSuffix;
    }

    public void setStreetSuffix(String streetSuffix) {
        this.streetSuffix = streetSuffix;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
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
        private String streetNumber;
        private String streetName;
        private String streetSuffix;
        private String postalCode;
        private String city;
        private String state;
        private String country;
        private boolean hidden;
        private LocalDateTime createdAt;
        private Customer customer;

        Builder() {
            super();
        }

        @SuppressWarnings("unchecked")
        public S setCustomer(Customer customer) {
            this.customer = customer;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setHidden(boolean hidden) {
            this.hidden = hidden;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setState(String state) {
            this.state = state;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setStreetName(String streetName) {
            this.streetName = streetName;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setStreetSuffix(String streetSuffix) {
            this.streetSuffix = streetSuffix;
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
        public S setStreetNumber(String streetNumber) {
            this.streetNumber = streetNumber;
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
            address.setHidden(this.hidden);
            address.setId(this.id);
            address.setState(this.state);
            address.setStreetNumber(this.streetNumber);
            address.setStreetName(this.streetName);
            address.setStreetSuffix(this.streetSuffix);
            address.setPostalCode(this.postalCode);
            return address;
        }
    }
}
