package com.practice.phonepe.questions.bookmyshowv2.entities;

public class ShowSeat {

    private final String showID;
    private final Seat seat;
    private final Double price;

    private SeatStatus seatStatus;
    private String lockedByUserId;
    private long lockExpiryEpochMillis;


    public ShowSeat(String showID, Seat seat, Double price) {
        this.showID = showID;
        this.seat = seat;
        this.price = price;
    }

    public String getId() {
        return showID + "_" + seat.getId();
    }

    public String getShowId() {
        return showID;
    }

    public Seat getSeat() {
        return seat;
    }

    public double getPrice() {
        return price;
    }

    public synchronized SeatStatus getStatus() {
        return seatStatus;
    }

    public synchronized String getLockedByUserId() {
        return lockedByUserId;
    }

    public synchronized boolean tryLock(String userID, long ttlMillis ,  long currentEpochTime) {
        // if free change status to locked and lockedbyuser = current user and expiryepoch time calcualte
        return true;
    }

    public synchronized boolean confirmBooking(String userId, long nowEpochMillis) {
        // locked and userid = lockedbyuserid and expiry time not reached
        return true;
    }

    public synchronized void release(String userID) {
        // remove lock and staus back to available
    }

    public synchronized boolean expireIfNeeded(long currentEpochMiills) {
        // if current epoch is greater then lockExpiryEpochMillis
        // remove the lock / user and status back to aviable
        return true;
    }
}
