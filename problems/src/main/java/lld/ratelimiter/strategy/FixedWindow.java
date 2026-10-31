package lld.ratelimiter.strategy;

public class FixedWindow {
    private int capacity;
    private int currentcapacity;
    private long nextRefillTime;
    private long windowDuration;

    public FixedWindow(int capacity, long windowDuration) {
        this.capacity = capacity;
        this.currentcapacity = 0;
        this.windowDuration = windowDuration;
        this.nextRefillTime = System.currentTimeMillis() + windowDuration;
    }

    public synchronized boolean isAllowed() {
        long now =  System.currentTimeMillis();
        if(now >= nextRefillTime) {
            nextRefillTime = now+ windowDuration;
            currentcapacity = 0;
        }
        if(currentcapacity < capacity) {
            currentcapacity++;
            return true;
        }
        return false;
    }
}
