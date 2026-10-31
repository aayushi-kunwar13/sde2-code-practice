package lld.ratelimiter.strategy;

import java.util.ArrayDeque;
import java.util.Deque;

public class SlidingWindow {

    private final int capacity;
    private long windowDurationMs;
    private final Deque<Long> timestamps = new ArrayDeque<>();
    //final on a reference field means the reference
    //can't be reassigned, not that the object it points to can't change. The two are separate things in Java.

    public SlidingWindow(int capacity, long windowDurationMs) {
        this.capacity = capacity;
        this.windowDurationMs = windowDurationMs;
    }

    public synchronized boolean isAllowed() {
        long now = System.currentTimeMillis();

        while(timestamps.isEmpty() && now - windowDurationMs >= timestamps.peekFirst()){
            timestamps.pollFirst();
        }

        if(timestamps.size() < capacity) {
            timestamps.addLast(now);
            return true;
        }

        return false;
    }

}
