package com.mikepaskual.delivery.customer.dto;

import java.time.LocalDate;

public class PackItem {

    private Long id;
    private String title;
    private LocalDate createdAt;
    private String receiver;
    private boolean discarded;

    public PackItem() {
        super();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isDiscarded() {
        return discarded;
    }

    public void setDiscarded(boolean discarded) {
        this.discarded = discarded;
    }

    @SuppressWarnings("rawtypes")
    public static Builder builder() {
        return new Builder();
    }

    @SuppressWarnings("rawtypes")
    public static class Builder<S extends Builder> {

        private Long id;
        private String title;
        private LocalDate createdAt;
        private String receiver;
        private boolean discarded;

        Builder() {
            super();
        }

        @SuppressWarnings("unchecked")
        public S setId(Long id) {
            this.id = id;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setTitle(String title) {
            this.title = title;
            return (S) this;
        }

        @SuppressWarnings("unchecked")
        public S setCreatedAt(LocalDate createdAt) {
            this.createdAt = createdAt;
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

        public PackItem build() {
            PackItem item = new PackItem();
            item.setCreatedAt(this.createdAt);
            item.setDiscarded(this.discarded);
            item.setId(this.id);
            item.setReceiver(this.receiver);
            item.setTitle(this.title);
            return item;
        }
    }
}
