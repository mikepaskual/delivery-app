package com.mikepaskual.delivery.customer.dto;

import java.time.LocalDateTime;

public class PackResume {

    private Long id;
    private Double weight;
    private String title;
    private String description;
    private String receiver;
    private boolean discarded;
    private LocalDateTime createdAt;

    public PackResume() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public boolean isDiscarded() {
        return discarded;
    }

    public void setDiscarded(boolean discarded) {
        this.discarded = discarded;
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
        private Double weight;
        private String title;
        private String description;
        private String receiver;
        private boolean discarded;
        private LocalDateTime createdAt;

        Builder() {
            super();
        }

        @SuppressWarnings("unchecked")
        public S setId(Long id) {
            this.id = id;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setWeight(Double weight) {
            this.weight = weight;
            return (S) this;
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
        public S setReceiver(String receiver) {
            this.receiver = receiver;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setDiscarded(boolean discarded) {
            this.discarded = discarded;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return (S) this;
        }

        public PackResume build() {
            PackResume resume = new PackResume();
            resume.setCreatedAt(this.createdAt);
            resume.setDescription(this.description);
            resume.setDiscarded(this.discarded);
            resume.setId(this.id);
            resume.setReceiver(this.receiver);
            resume.setTitle(this.title);
            resume.setWeight(this.weight);
            return resume;
        }
    }
}
