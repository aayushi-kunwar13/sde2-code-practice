package com.practice.phonepe.questions.bookmyshow.entities;

/**
 * Per-show mutable booking state for a physical {@link Seat}. The same seat is reused across
 * many shows, so availability/locking is tracked here instead of on the seat itself.
 */
public class ShowSeat {

    private final String showId;
    private final Seat seat;
    private final double price;

    private SeatStatus status;
    private String lockedByUserId;
    private long lockExpiryEpochMillis;

    public ShowSeat(String showId, Seat seat, double price) {
        this.showId = showId;
        this.seat = seat;
        this.price = price;
        this.status = SeatStatus.AVAILABLE;
    }

    public String getId() {
        return showId + "_" + seat.getId();
    }

    public String getShowId() {
        return showId;
    }

    public Seat getSeat() {
        return seat;
    }

    public double getPrice() {
        return price;
    }

    public synchronized SeatStatus getStatus() {
        return status;
    }

    public synchronized String getLockedByUserId() {
        return lockedByUserId;
    }

    /** Attempts to move AVAILABLE -> LOCKED for the given user. Self-heals stale locks that outlived their TTL. */
    public synchronized boolean tryLock(String userId, long ttlMillis, long nowEpochMillis) {
        expireIfNeeded(nowEpochMillis);
        if (status != SeatStatus.AVAILABLE) {
            return false;
        }
        status = SeatStatus.LOCKED;
        lockedByUserId = userId;
        lockExpiryEpochMillis = nowEpochMillis + ttlMillis;
        return true;
    }

    /** Moves LOCKED -> BOOKED, only if still held by the same user and not expired. */
    public synchronized boolean confirmBooking(String userId, long nowEpochMillis) {
        expireIfNeeded(nowEpochMillis);
        if (status != SeatStatus.LOCKED || !lockedByUserId.equals(userId)) {
            return false;
        }
        status = SeatStatus.BOOKED;
        return true;
    }

    /** Releases a LOCKED seat held by the given user back to AVAILABLE (cancellation / payment failure). */
    public synchronized void release(String userId) {
        if (status == SeatStatus.LOCKED && userId.equals(lockedByUserId)) {
            status = SeatStatus.AVAILABLE;
            lockedByUserId = null;
        }
    }

    /** Called by the background sweep; releases an expired lock back to AVAILABLE. Returns true if it did. */
    public synchronized boolean expireIfNeeded(long nowEpochMillis) {
        if (status == SeatStatus.LOCKED && nowEpochMillis >= lockExpiryEpochMillis) {
            status = SeatStatus.AVAILABLE;
            lockedByUserId = null;
            return true;
        }
        return false;
    }
}
