package com.mikepaskual.delivery.customer.dto;

import jakarta.validation.constraints.*;

public class CreatePackRequest {

    @NotNull(message = "{pack.validation.weight.notNull}")
    @DecimalMin(value = "2.0", message = "{pack.validation.weight.min}")
    @DecimalMax(value = "500.0", message = "{pack.validation.weight.max}")
    private Double weight;
    @NotEmpty(message = "{pack.validation.title.notEmpty}")
    @Size(max = 50, message = "pack.validation.title.max")
    private String title;
    private String description;
    @NotNull(message = "{pack.validation.receiver.notNull}")
    private Long receiver;

    public CreatePackRequest() {
        super();
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @SuppressWarnings("rawtypes")
    public static Builder builder() {
        return new Builder();
    }

    @SuppressWarnings("rawtypes")
    public static class Builder<S extends Builder> {

        private Double weight;
        private Long receiver;
        private String title;
        private String description;

        Builder() {
            super();
        }

        @SuppressWarnings("unchecked")
        public S setTitle(String title) {
            this.title = title;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setDescription(String description) {
            this.description = description;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setReceiver(Long receiver) {
            this.receiver = receiver;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setWeight(Double weight) {
            this.weight = weight;
            return (S) this;
        }

        public CreatePackRequest build() {
            CreatePackRequest request = new CreatePackRequest();
            request.setDescription(this.description);
            request.setReceiver(this.receiver);
            request.setTitle(this.title);
            request.setWeight(this.weight);
            return request;
        }
    }
}
