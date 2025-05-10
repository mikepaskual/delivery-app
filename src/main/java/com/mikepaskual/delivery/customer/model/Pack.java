package com.mikepaskual.delivery.customer.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pack_entity")
public class Pack {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private Double weight;
    private boolean discarded;
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Customer sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Customer receiver;

    public Pack() {
        super();
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Customer getSender() {
        return sender;
    }

    public void setSender(Customer sender) {
        this.sender = sender;
    }

    public Customer getReceiver() {
        return receiver;
    }

    public void setReceiver(Customer receiver) {
        this.receiver = receiver;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDiscarded() {
        return discarded;
    }

    public void setDiscarded(boolean discarded) {
        this.discarded = discarded;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @SuppressWarnings("rawtypes")
    public static Builder builder() {
        return new Builder();
    }

    @SuppressWarnings("rawtypes")
    public static class Builder<S extends Builder> {

        private Long id;
        private String title;
        private String description;
        private Double weight;
        private boolean discarded;
        private LocalDateTime createdAt;
        private Customer sender;
        private Customer receiver;

        Builder() {
            super();
        }

        @SuppressWarnings("unchecked")
        public S setDiscarded(boolean discarded) {
            this.discarded = discarded;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setTitle(String title) {
            this.title = title;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setWeight(Double weight) {
            this.weight = weight;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setSender(Customer sender) {
            this.sender = sender;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setReceiver(Customer receiver) {
            this.receiver = receiver;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setId(Long id) {
            this.id = id;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setDescription(String description) {
            this.description = description;
            return (S) this;
        }

        public Pack build() {
            Pack pack = new Pack();
            pack.setCreatedAt(this.createdAt);
            pack.setDescription(this.description);
            pack.setDiscarded(this.discarded);
            pack.setId(this.id);
            pack.setReceiver(this.receiver);
            pack.setSender(this.sender);
            pack.setTitle(this.title);
            pack.setWeight(this.weight);
            return pack;
        }
    }
}
