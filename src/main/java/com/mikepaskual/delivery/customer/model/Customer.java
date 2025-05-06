package com.mikepaskual.delivery.customer.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "customer_entity")
public class Customer {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime createdAt;

    public Customer() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        private LocalDateTime createdAt;

        public Builder() {
            super();
        }

        @SuppressWarnings("unchecked")
        public S setId(Long id) {
            this.id = id;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return (S) this;
        }

        public Customer build() {
            Customer customer = new Customer();
            customer.setCreatedAt(this.createdAt);
            customer.setId(this.id);
            return customer;
        }
    }
}
