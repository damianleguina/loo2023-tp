package com.loo.tp.entities;

import java.time.Instant;

import com.loo.tp.enums.PrintQuality;
import com.loo.tp.enums.PrintStatus;

public class Print extends Entity {
    private long userId;
    private PrintQuality quality;
    private int quantity;
    private PrintStatus status;
    private Instant startDate;
    private Instant endDate;
    private Instant deliveryDate;
    private User user;

    public Print(long id, long userId, PrintQuality quality, int quantity, PrintStatus status, Instant startDate, Instant endDate,
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

    public PrintQuality getQuality() {
        return quality;
    }

    public void setQuality(PrintQuality quality) {
        this.quality = quality;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public PrintStatus getStatus() {
        return status;
    }

    public void setStatus(PrintStatus status) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
