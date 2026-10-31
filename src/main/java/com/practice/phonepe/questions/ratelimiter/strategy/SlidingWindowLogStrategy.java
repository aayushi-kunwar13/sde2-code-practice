package com.practice.phonepe.questions.ratelimiter.strategy;

import com.practice.phonepe.questions.ratelimiter.RateLimitStore;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The simplest sliding window to reason about: keep every request's timestamp, evict
 * anything older than windowMillis, and just count what's left -- no weighting math, no
 * approximation, exact. The cost is memory: one entry per request currently inside the
 * window, instead of one counter.
 *
 * Like TokenBucketStrategy, this keeps its own local state rather than going through
 * RateLimitStore -- a list of timestamps doesn't fit a simple increment-a-counter contract.
 * A distributed version of this does exist (Redis sorted sets: ZADD + ZREMRANGEBYSCORE +
 * ZCARD), it just needs a different store shape than the one built here.
 */
public final class SlidingWindowLogStrategy implements RateLimitAlgorithm {

    private final ConcurrentHashMap<String, Deque<Long>> requestLogs = new ConcurrentHashMap<>();

    @Override
    public boolean allowRequest(RateLimitStore store, String clientId, int maxRequests, long windowMillis) {
        Deque<Long> timestamps = requestLogs.computeIfAbsent(clientId, id -> new ArrayDeque<>());

        synchronized (timestamps) {
            long now = System.currentTimeMillis();
            evictOld(timestamps, now, windowMillis);

            if (timestamps.size() < maxRequests) {
                timestamps.addLast(now);
                return true;
            }
            return false;
        }
    }

    private void evictOld(Deque<Long> timestamps, long now, long windowMillis) {
        while (!timestamps.isEmpty() && timestamps.peekFirst() <= now - windowMillis) {
            timestamps.pollFirst();
        }
    }
}
