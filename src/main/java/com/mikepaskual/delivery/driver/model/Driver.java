package com.mikepaskual.delivery.driver.model;

import com.mikepaskual.delivery.truck.model.Truck;
import com.mikepaskual.delivery.user.model.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
    private LocalDateTime createdAt;
    private String internalNotes;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private List<Truck> trucks;

    public Driver() {
        trucks = new ArrayList<>();
    }

    public List<Truck> getTrucks() {
        return trucks;
    }

    public void setTrucks(List<Truck> trucks) {
        if (trucks == null) {
            this.trucks = new ArrayList<>();
        } else {
            this.trucks = new ArrayList<>(trucks);
        }
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

    public String getInternalNotes() {
        return internalNotes;
    }

    public void setInternalNotes(String internalNotes) {
        this.internalNotes = internalNotes;
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
        private LocalDateTime createdAt;
        private String internalNotes;
        private List<Truck> trucks;

        Builder() {
            super();
        }

        @SuppressWarnings("unchecked")
        public S setTrucks(List<Truck> trucks) {
            this.trucks = trucks;
            return (S) this;
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
        public S setInternalNotes(String internalNotes) {
            this.internalNotes = internalNotes;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return (S) this;
        }

        public Driver build() {
            Driver driver = new Driver();
            driver.setAvailableFrom(this.availableFrom);
            driver.setAvailableTo(this.availableTo);
            driver.setCreatedAt(this.createdAt);
            driver.setId(this.id);
            driver.setLicenseNumber(this.licenseNumber);
            driver.setInternalNotes(this.internalNotes);
            driver.setStatus(this.status);
            driver.setTrucks(this.trucks);
            driver.setUser(this.user);
            return driver;
        }
    }
}
