package com.practice.phonepe.questions.bookmyshow.entities;

import java.util.UUID;

public class Payment {

    private final String id;
    private final String bookingId;
    private final double amount;
    private final String method;
    private PaymentStatus status;

    public Payment(String bookingId, double amount, String method, PaymentStatus status) {
        this.id = UUID.randomUUID().toString();
        this.bookingId = bookingId;
        this.amount = amount;
        this.method = method;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public double getAmount() {
        return amount;
    }

    public String getMethod() {
        return method;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
