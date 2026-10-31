package com.practice.phonepe.questions.ratelimiter.strategy;

import com.practice.phonepe.questions.ratelimiter.RateLimitStore;

/**
 * Smooths out fixed window's boundary-burst problem without paying for a full request log:
 * estimate the current count as a weighted blend of the previous window's count and this
 * window's count so far, weighted by how far into the current window we are.
 */
public final class SlidingWindowCounterStrategy implements RateLimitAlgorithm {

    @Override
    public boolean allowRequest(RateLimitStore store, String clientId, int maxRequests, long windowMillis) {
        long now = System.currentTimeMillis();
        long currentWindowStart = (now / windowMillis) * windowMillis;
        long previousWindowStart = currentWindowStart - windowMillis;

        double elapsedInCurrent = now - currentWindowStart;
        double weightOfPrevious = (windowMillis - elapsedInCurrent) / (double) windowMillis;

        long previousCount = store.get(clientId + ":" + previousWindowStart);
        long currentCount = store.incrementAndGet(clientId + ":" + currentWindowStart);

        double estimatedCount = previousCount * weightOfPrevious + currentCount;
        return estimatedCount <= maxRequests;
    }
}
