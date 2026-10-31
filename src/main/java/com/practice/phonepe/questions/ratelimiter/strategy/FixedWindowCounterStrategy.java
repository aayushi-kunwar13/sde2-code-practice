package com.practice.phonepe.questions.ratelimiter.strategy;

import com.practice.phonepe.questions.ratelimiter.RateLimitStore;

/**
 * Simplest possible algorithm: bucket time into fixed windows, count requests in the current
 * one. One weakness worth knowing: a client can burst up to 2x the limit right around a
 * window boundary (max at the tail of one window, max again at the head of the next).
 */
public final class FixedWindowCounterStrategy implements RateLimitAlgorithm {

    @Override
    public boolean allowRequest(RateLimitStore store, String clientId, int maxRequests, long windowMillis) {
        long windowStart = (System.currentTimeMillis() / windowMillis) * windowMillis;
        String key = clientId + ":" + windowStart;
        long count = store.incrementAndGet(key);
        return count <= maxRequests;
    }
}
