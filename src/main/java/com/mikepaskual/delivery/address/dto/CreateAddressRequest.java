package com.mikepaskual.delivery.address.dto;

import java.time.LocalDateTime;

public class CreateAddressRequest {

    private Long id;
    private String streetNumber;
    private String streetName;
    private String streetSuffix;
    private String postalCode;
    private String city;
    private String state;
    private String country;
    private LocalDateTime createdAt;

    public CreateAddressRequest() {
        super();
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
        private LocalDateTime createdAt;

        public Builder() {
            super();
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
        public S setState(String state) {
            this.state = state;
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

        @SuppressWarnings("unchecked")
        public S setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return (S) this;
        }

        public CreateAddressRequest build() {
            CreateAddressRequest request = new CreateAddressRequest();
            request.setCity(this.city);
            request.setCountry(this.country);
            request.setCreatedAt(this.createdAt);
            request.setId(this.id);
            request.setState(this.state);
            request.setStreetName(this.streetName);
            request.setStreetNumber(this.streetNumber);
            request.setStreetSuffix(this.streetSuffix);
            request.setPostalCode(this.postalCode);
            return request;
        }
    }
}
