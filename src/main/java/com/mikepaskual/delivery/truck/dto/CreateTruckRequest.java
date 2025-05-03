package com.mikepaskual.delivery.truck.dto;

import com.mikepaskual.delivery.shared.validation.SelectionValue;
import com.mikepaskual.delivery.truck.model.Truck;
import com.mikepaskual.delivery.truck.validation.YearRange;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class CreateTruckRequest {

    private Long id;
    @NotBlank(message = "{truck.validation.make.notBlank}")
    @Size(min = 2, max = 50, message = "{truck.validation.make.size}")
    private String make;
    @NotBlank(message = "{truck.validation.model.notBlank}")
    @Size(min = 2, max = 50, message = "{truck.validation.model.size}")
    private String model;
    @NotNull(message = "{truck.validation.year.notNull}")
    @YearRange
    private Integer year;
    @NotBlank(message = "{truck.validation.plate.notBlank}")
    @Pattern(regexp = "^\\d{4} [BCDFGHJKLMNPQRSTVWXYZ]{3}$", message = "{truck.validation.plate.pattern}")
    private String plate;
    @NotBlank(message = "{truck.validation.color.notBlank}")
    @Size(min = 2, max = 50, message = "{truck.validation.color.size}")
    private String color;
    private String status;
    @SelectionValue
    private String transmission;
    @SelectionValue
    private String fuelType;
    @NotNull(message = "{truck.validation.capacity.notNull}")
    @Min(value = 5, message = "{truck.validation.capacity.min}")
    @Max(value = 500, message = "{truck.validation.capacity.max}")
    private Integer capacity;
    @NotNull(message = "{truck.validation.length.notNull}")
    @DecimalMin(value = "2.0", message = "{truck.validation.length.min}")
    @DecimalMax(value = "25.0", message = "{truck.validation.length.max}")
    private Double length;
    @NotNull(message = "{truck.validation.width.notNull}")
    @DecimalMin(value = "1.0", message = "{truck.validation.width.min}")
    @DecimalMax(value = "4.5", message = "{truck.validation.width.max}")
    private Double width;
    @NotNull(message = "{truck.validation.height.notNull}")
    @DecimalMin(value = "1.5", message = "{truck.validation.height.min}")
    @DecimalMax(value = "5.0", message = "{truck.validation.height.max}")
    private Double height;
    @NotNull(message = "{truck.validation.purchaseDate.notNull}")
    @Past(message = "{truck.validation.purchaseDate.past}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate purchaseDate;

    public static CreateTruckRequest of(Truck truck) {
        return CreateTruckRequest.builder()
                .setCapacity(truck.getCapacity())
                .setColor(truck.getColor())
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        private String status;
        private String transmission;
        private String fuelType;
        private Integer capacity;
        private Double length;
        private Double width;
        private Double height;
        private LocalDate purchaseDate;

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
        public S setStatus(String status) {
            this.status = status;
            return (S) this;
        }

        public CreateTruckRequest build() {
            CreateTruckRequest request = new CreateTruckRequest();
            request.setCapacity(this.capacity);
            request.setColor(this.color);
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
