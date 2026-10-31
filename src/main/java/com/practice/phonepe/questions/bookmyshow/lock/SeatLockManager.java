package com.practice.phonepe.questions.bookmyshow.lock;

import com.practice.phonepe.questions.bookmyshow.entities.ShowSeat;
import com.practice.phonepe.questions.bookmyshow.exception.SeatUnavailableException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Coordinates temporary seat holds while a user is checking out. A seat picked by one user is
 * LOCKED for a short TTL so nobody else can grab it; if checkout doesn't complete in time, a
 * background sweep releases it automatically so it becomes bookable again. This is what keeps
 * seat availability correct under concurrent bookings without holding the lock forever.
 */
public class SeatLockManager {

    private static final long DEFAULT_TTL_MILLIS = TimeUnit.MINUTES.toMillis(5);
    private static final long SWEEP_INTERVAL_MILLIS = TimeUnit.SECONDS.toMillis(1);

    private final long lockTtlMillis;
    private final ConcurrentHashMap<String, ShowSeat> activeLocks = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r, "seat-lock-sweeper");
        t.setDaemon(true);
        return t;
    });

    public SeatLockManager() {
        this(DEFAULT_TTL_MILLIS);
    }

    public SeatLockManager(long lockTtlMillis) {
        this.lockTtlMillis = lockTtlMillis;
        scheduler.scheduleAtFixedRate(this::sweepExpiredLocks, SWEEP_INTERVAL_MILLIS, SWEEP_INTERVAL_MILLIS, TimeUnit.MILLISECONDS);
    }

    /**
     * Locks every seat for the given user, or none at all. If any requested seat is already
     * taken, whatever was locked earlier in this call is rolled back before throwing.
     */
    public List<ShowSeat> lockSeats(String userId, List<ShowSeat> seats) {
        long now = System.currentTimeMillis();
        List<ShowSeat> locked = new ArrayList<>();
        for (ShowSeat seat : seats) {
            if (seat.tryLock(userId, lockTtlMillis, now)) {
                locked.add(seat);
                activeLocks.put(seat.getId(), seat);
            } else {
                releaseSeats(userId, locked);
                throw new SeatUnavailableException("Seat " + seat.getSeat().getLabel() + " is no longer available");
            }
        }
        return locked;
    }

    /** Moves held seats from LOCKED to BOOKED once payment succeeds. */
    public void confirmSeats(String userId, List<ShowSeat> seats) {
        long now = System.currentTimeMillis();
        for (ShowSeat seat : seats) {
            seat.confirmBooking(userId, now);
            activeLocks.remove(seat.getId());
        }
    }

    /** Releases held seats back to AVAILABLE, e.g. on cancellation or payment failure. */
    public void releaseSeats(String userId, List<ShowSeat> seats) {
        for (ShowSeat seat : seats) {
            seat.release(userId);
            activeLocks.remove(seat.getId());
        }
    }

    private void sweepExpiredLocks() {
        long now = System.currentTimeMillis();
        activeLocks.forEach((id, seat) -> {
            if (seat.expireIfNeeded(now)) {
                activeLocks.remove(id);
            }
        });
    }

    public void shutdown() {
        scheduler.shutdownNow();
    }
}
