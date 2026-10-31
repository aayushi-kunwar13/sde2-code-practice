package com.practice.phonepe.questions.bookmyshow.entities;

import java.util.List;
import java.util.UUID;

public class Booking {

    private final String id;
    private final String userId;
    private final String showId;
    private final List<String> seatIds;
    private final double totalAmount;
    private BookingStatus status;
    private Payment payment;

    public Booking(String userId, String showId, List<String> seatIds, double totalAmount) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.showId = showId;
        this.seatIds = seatIds;
        this.totalAmount = totalAmount;
        this.status = BookingStatus.CREATED;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getShowId() {
        return showId;
    }

    public List<String> getSeatIds() {
        return seatIds;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
