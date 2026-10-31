package lld.ratelimiter.strategy;

public class TokenBucket {
    private final int capacity;
    private final double tokenRefillRatePerMs;
    public double tokens;
    private long lastRefillTime;

    public TokenBucket(int capacity, double tokenRefillRatePerSecond) {
        this.capacity = capacity;
        this.tokenRefillRatePerMs = tokenRefillRatePerSecond / 1000.0;
        this.tokens = capacity;
        this.lastRefillTime = System.currentTimeMillis();
    }

    public synchronized boolean isAllowed() {
        refill();
        if(tokens >= 1) {
            tokens--;
            return true;
        }
        return false;
    }

    private void refill() {
        long now = System.currentTimeMillis();
        tokens = Math.min(capacity , tokens + (now - lastRefillTime)*tokenRefillRatePerMs);
        lastRefillTime = now;
    }
}
