package com.mikepaskual.delivery.driver.dto;

import com.mikepaskual.delivery.driver.validation.AvailableHours;
import com.mikepaskual.delivery.driver.validation.UniqueLicenseNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@AvailableHours
public class UpdateDriverRequest {

    @NotBlank(message = "{driver.validation.licenseNumber.notBlank}")
    @Pattern(regexp = "^[A-Z]{3}[0-9]{10}$", message = "{driver.validation.licenseNumber.pattern}")
    @UniqueLicenseNumber
    private String licenseNumber;
    @NotNull(message = "{driver.validation.availableFrom.notNull}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime availableFrom;
    @NotNull(message = "{driver.validation.availableTo.notNull}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime availableTo;

    public UpdateDriverRequest() {
        super();
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public LocalTime getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(LocalTime availableFrom) {
        this.availableFrom = availableFrom;
    }

    public LocalTime getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(LocalTime availableTo) {
        this.availableTo = availableTo;
    }

    @SuppressWarnings("rawtypes")
    public static Builder builder() {
        return new Builder();
    }

    @SuppressWarnings("rawtypes")
    public static class Builder<S extends Builder> {

        private String licenseNumber;
        private LocalTime availableFrom;
        private LocalTime availableTo;

        Builder() {
            super();
        }

        @SuppressWarnings("unchecked")
        public S setLicenseNumber(String licenseNumber) {
            this.licenseNumber = licenseNumber;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setAvailableFrom(LocalTime availableFrom) {
            this.availableFrom = availableFrom;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setAvailableTo(LocalTime availableTo) {
            this.availableTo = availableTo;
            return (S) this;
        }

        public UpdateDriverRequest build() {
            UpdateDriverRequest request = new UpdateDriverRequest();
            request.setAvailableFrom(this.availableFrom);
            request.setAvailableTo(this.availableTo);
            request.setLicenseNumber(this.licenseNumber);
            return request;
        }
    }
}
