package com.mikepaskual.delivery.driver.model;

import com.mikepaskual.delivery.user.model.User;
import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "driver_entity")
public class Driver {

    @Id
    private Long id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;
    private String licenseNumber;
    @Enumerated(EnumType.STRING)
    private DriverStatus status;
    private LocalTime availableFrom;
    private LocalTime availableTo;
    private String notes;

    public Driver() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @SuppressWarnings("rawtypes")
    public static Builder builder() {
        return new Builder();
    }

    @SuppressWarnings("rawtypes")
    public static class Builder<S extends Builder> {

        private Long id;
        private User user;
        private String licenseNumber;
        private DriverStatus status;
        private LocalTime availableFrom;
        private LocalTime availableTo;
        private String notes;

        Builder() {
            super();
        }

        @SuppressWarnings("unchecked")
        public S setId(Long id) {
            this.id = id;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setUser(User user) {
            this.user = user;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setLicenseNumber(String licenseNumber) {
            this.licenseNumber = licenseNumber;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setStatus(DriverStatus status) {
            this.status = status;
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

        @SuppressWarnings("unchecked")
        public S setNotes(String notes) {
            this.notes = notes;
            return (S) this;
        }

        public Driver build() {
            Driver driver = new Driver();
            driver.setAvailableFrom(this.availableFrom);
            driver.setAvailableTo(this.availableTo);
            driver.setId(this.id);
            driver.setLicenseNumber(this.licenseNumber);
            driver.setNotes(this.notes);
            driver.setStatus(this.status);
            driver.setUser(this.user);
            return driver;
        }
    }
}
