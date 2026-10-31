package com.practice.phonepe.questions.ratelimiter;

/** How many requests a single entity may make within a window -- configurable per entity. */
public final class RateLimitConfig {

    private final int maxRequests;
    private final long windowMillis;

    public RateLimitConfig(int maxRequests, long windowMillis) {
        this.maxRequests = maxRequests;
        this.windowMillis = windowMillis;
    }

    public int getMaxRequests() {
        return maxRequests;
    }

    public long getWindowMillis() {
        return windowMillis;
    }
}
