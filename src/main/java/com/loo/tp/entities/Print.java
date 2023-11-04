package com.loo.tp.entities;

import java.time.Instant;

public class Print extends Entity {
    enum Quality {
        BLACK_AND_WHITE,
        COLOR,
    }

    enum Status {
        PENDING,
        RECEIVED,
        IN_PROGRESS,
        FINISHED,
        DELIVERED
    }

    private long userId;
    private Quality quality;
    private int quantity;
    private Status status;
    private Instant startDate;
    private Instant endDate;
    private Instant deliveryDate;

    public Print(long id, long userId, Quality quality, int quantity, Status status, Instant startDate, Instant endDate,
            Instant deliveryDate) {
        super(id);
        this.userId = userId;
        this.quality = quality;
        this.quantity = quantity;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deliveryDate = deliveryDate;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Instant getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Instant deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
