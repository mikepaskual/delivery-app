package com.mikepaskual.delivery.truck.dto;

import com.mikepaskual.delivery.driver.model.Driver;
import com.mikepaskual.delivery.truck.model.Truck;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreateTruckRequest {

    private Long id;
    private String make;
    private String model;
    private Integer year;
    private String plate;
    private String color;
    private String transmission;
    private String fuelType;
    private Integer capacity;
    private Double length;
    private Integer width;
    private Double height;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate purchaseDate;
    private String status;
    private LocalDateTime createdAt;
    private Driver driver;

    public static CreateTruckRequest of(Truck truck) {
        return CreateTruckRequest.builder()
                .setCapacity(truck.getCapacity())
                .setCreatedAt(truck.getCreatedAt())
                .setColor(truck.getColor())
                .setDriver(truck.getDriver())
                .setFuelType(truck.getFuelType().name())
                .setHeight(truck.getHeight())
                .setId(truck.getId())
                .setLength(truck.getLength())
                .setMake(truck.getMake())
                .setModel(truck.getModel())
                .setPlate(truck.getPlate())
                .setPurchaseDate(truck.getPurchaseDate())
                .setStatus(truck.getStatus().name())
                .setTransmission(truck.getTransmission().name())
                .setWidth(truck.getWidth())
                .setYear(truck.getYear()).build();
    }

    public CreateTruckRequest() {
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

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
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

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
        private String transmission;
        private String fuelType;
        private Integer capacity;
        private Double length;
        private Integer width;
        private Double height;
        private LocalDate purchaseDate;
        private String status;
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
        public S setTransmission(String transmission) {
            this.transmission = transmission;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setFuelType(String fuelType) {
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
        public S setWidth(Integer width) {
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
        public S setStatus(String status) {
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

        public CreateTruckRequest build() {
            CreateTruckRequest request = new CreateTruckRequest();
            request.setCapacity(this.capacity);
            request.setColor(this.color);
            request.setCreatedAt(this.createdAt);
            request.setDriver(this.driver);
            request.setFuelType(this.fuelType);
            request.setHeight(this.height);
            request.setId(this.id);
            request.setLength(this.length);
            request.setMake(this.make);
            request.setModel(this.model);
            request.setPlate(this.plate);
            request.setPurchaseDate(this.purchaseDate);
            request.setStatus(this.status);
            request.setTransmission(this.transmission);
            request.setWidth(this.width);
            request.setYear(this.year);
            return request;
        }
    }
}
