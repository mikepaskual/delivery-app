package com.mikepaskual.delivery.customer.model;

import com.mikepaskual.delivery.user.model.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer_entity")
public class Customer {

    @Id
    private Long id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;
    @OneToMany(mappedBy = "sender")
    private List<Pack> sentPacks;
    @OneToMany(mappedBy = "receiver")
    private List<Pack> receivedPacks;
    private LocalDateTime createdAt;

    public Customer() {
        super();
    }

    public List<Pack> getReceivedPacks() {
        return receivedPacks;
    }

    public void setReceivedPacks(List<Pack> receivedPacks) {
        if (receivedPacks == null) {
            this.receivedPacks = new ArrayList<>();
        } else {
            this.receivedPacks = new ArrayList<>(receivedPacks);
        }
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Pack> getSentPacks() {
        return sentPacks;
    }

    public void setSentPacks(List<Pack> sentPacks) {
        if (sentPacks == null) {
            this.sentPacks = new ArrayList<>();
        } else {
            this.sentPacks = new ArrayList<>(sentPacks);
        }
    }

    @SuppressWarnings("rawtypes")
    public static Builder builder() {
        return new Builder();
    }

    @SuppressWarnings("rawtypes")
    public static class Builder<S extends Builder> {

        private Long id;
        private User user;
        private LocalDateTime createdAt;
        private List<Pack> sentPacks;
        private List<Pack> receivedPacks;

        public Builder() {
            super();
        }

        @SuppressWarnings("unchecked")
        public S setReceivedPacks(List<Pack> receivedPacks) {
            this.receivedPacks = receivedPacks;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setUser(User user) {
            this.user = user;
            return (S) this;
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

        @SuppressWarnings("unchecked")
        public S setSentPacks(List<Pack> sentPacks) {
            this.sentPacks = sentPacks;
            return (S) this;
        }

        public Customer build() {
            Customer customer = new Customer();
            customer.setCreatedAt(this.createdAt);
            customer.setId(this.id);
            customer.setReceivedPacks(this.receivedPacks);
            customer.setSentPacks(this.sentPacks);
            customer.setUser(this.user);
            return customer;
        }
    }
}
