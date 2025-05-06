package com.mikepaskual.delivery.address.dto;

import com.mikepaskual.delivery.address.model.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateAddressRequest {

    @NotBlank(message = "{address.validation.street.notBlank}")
    @Size(max = 100, message = "{address.validation.street.maxSize}")
    private String street;
    @NotBlank(message = "{address.validation.postalCode.notBlank}")
    @Pattern(regexp = "^[A-Za-z0-9][A-Za-z0-9\\- ]{1,8}[A-Za-z0-9]$", message = "{address.validation.postalCode.pattern}")
    private String postalCode;
    @NotBlank(message = "{address.validation.city.notBlank}")
    @Size(max = 50, message = "{address.validation.city.maxSize}")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ'\\- ]+$", message = "{address.validation.city.pattern}")
    private String city;
    @Size(max = 50, message = "{address.validation.state.maxSize}")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ'\\- ]*$", message = "{address.validation.state.pattern}")
    private String state;
    @NotBlank(message = "{address.validation.country.notBlank}")
    @Size(max = 56, message = "{address.validation.country.maxSize}")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ'\\- ]+$", message = "{address.validation.country.pattern}")
    private String country;

    public static CreateAddressRequest of(Address address) {
        return CreateAddressRequest.builder()
                .setCity(address.getCity())
                .setCountry(address.getCountry())
                .setPostalCode(address.getPostalCode())
                .setState(address.getState())
                .setStreet(address.getStreet()).build();
    }

    public CreateAddressRequest() {
        super();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    @SuppressWarnings("rawtypes")
    public static Builder builder() {
        return new Builder();
    }

    @SuppressWarnings("rawtypes")
    public static class Builder<S extends Builder> {

        private String street;
        private String postalCode;
        private String city;
        private String state;
        private String country;

        public Builder() {
            super();
        }

        @SuppressWarnings("unchecked")
        public S setStreet(String street) {
            this.street = street;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setState(String state) {
            this.state = state;
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

        public CreateAddressRequest build() {
            CreateAddressRequest request = new CreateAddressRequest();
            request.setCity(this.city);
            request.setCountry(this.country);
            request.setPostalCode(this.postalCode);
            request.setState(this.state);
            request.setStreet(this.street);
            return request;
        }
    }
}
