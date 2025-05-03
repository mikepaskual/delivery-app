package com.mikepaskual.delivery.truck.model;

import com.mikepaskual.delivery.driver.model.Driver;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "truck_entity")
public class Truck {

    @Id
    @GeneratedValue
    private Long id;
    private String make;
    private String model;
    @Column(name = "truck_year")
    private Integer year;
    private String plate;
    private String color;
    @Enumerated(EnumType.STRING)
    private Transmission transmission;
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;
    private Integer capacity;
    private Double length;
    private Double width;
    private Double height;
    private LocalDate purchaseDate;
    @Enumerated(EnumType.STRING)
    private StatusTruck status;
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    public Truck() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public StatusTruck getStatus() {
        return status;
    }

    public void setStatus(StatusTruck status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @SuppressWarnings("rawtypes")
    public static Builder builder() {
        return new Builder();
    }

    @SuppressWarnings("rawtypes")
    public static class Builder<S extends Builder> {

        private Long id;
        private String make;
        private String model;
        private Integer year;
        private String plate;
        private String color;
        private Transmission transmission;
        private FuelType fuelType;
        private Integer capacity;
        private Double length;
        private Double width;
        private Double height;
        private LocalDate purchaseDate;
        private StatusTruck status;
        private LocalDateTime createdAt;
        private Driver driver;

        Builder() {
            super();
        }

        @SuppressWarnings("unchecked")
        public S setId(Long id) {
            this.id = id;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setMake(String make) {
            this.make = make;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setModel(String model) {
            this.model = model;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setYear(Integer year) {
            this.year = year;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setPlate(String plate) {
            this.plate = plate;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setColor(String color) {
            this.color = color;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setTransmission(Transmission transmission) {
            this.transmission = transmission;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setFuelType(FuelType fuelType) {
            this.fuelType = fuelType;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setCapacity(Integer capacity) {
            this.capacity = capacity;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setLength(Double length) {
            this.length = length;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setWidth(Double width) {
            this.width = width;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setHeight(Double height) {
            this.height = height;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setPurchaseDate(LocalDate purchaseDate) {
            this.purchaseDate = purchaseDate;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setStatus(StatusTruck status) {
            this.status = status;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setDriver(Driver driver) {
            this.driver = driver;
            return (S) this;
        }

        public Truck build() {
            Truck truck = new Truck();
            truck.setCapacity(this.capacity);
            truck.setColor(this.color);
            truck.setCreatedAt(this.createdAt);
            truck.setDriver(this.driver);
            truck.setFuelType(this.fuelType);
            truck.setHeight(this.height);
            truck.setId(this.id);
            truck.setLength(this.length);
            truck.setMake(this.make);
            truck.setModel(this.model);
            truck.setPlate(this.plate);
            truck.setPurchaseDate(this.purchaseDate);
            truck.setStatus(this.status);
            truck.setTransmission(this.transmission);
            truck.setWidth(this.width);
            truck.setYear(this.year);
            return truck;
        }
    }
}
