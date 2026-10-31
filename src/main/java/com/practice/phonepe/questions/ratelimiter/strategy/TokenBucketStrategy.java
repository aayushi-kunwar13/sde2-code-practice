package com.practice.phonepe.questions.ratelimiter.strategy;


import com.practice.phonepe.questions.ratelimiter.RateLimitStore;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Unlike the other two strategies, this one ignores the RateLimitStore parameter entirely.
 * Token bucket needs a compound read-modify-write -- tokens AND a last-refill timestamp,
 * recomputed together based on elapsed time -- and a simple incrementAndGet/get pair can't
 * express that atomically. So it keeps its own per-client state locally instead, which means
 * it's node-local only: two servers each running this strategy would enforce the limit
 * independently, not as one shared quota. Making it truly distributed would need either a
 * richer store primitive (an atomic compare-and-swap on a compound value) or a Redis Lua
 * script doing the refill-and-deduct server-side in a single round trip.
 *
 * maxRequests is treated as both the bucket's capacity (burst size) and the number of tokens
 * that refill over windowMillis -- so it fully refills once per window, same meaning as
 * "maxRequests per windowMillis" in the other two strategies.
 */
public final class TokenBucketStrategy implements RateLimitAlgorithm {

    private static final class Bucket {
        double availableTokens;
        long lastRefillTimestamp;

        Bucket(double availableTokens, long lastRefillTimestamp) {
            this.availableTokens = availableTokens;
            this.lastRefillTimestamp = lastRefillTimestamp;
        }
    }

    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Override
    public boolean allowRequest(RateLimitStore store, String clientId, int maxRequests, long windowMillis) {
        Bucket bucket = buckets.computeIfAbsent(clientId,
                id -> new Bucket(maxRequests, System.currentTimeMillis()));

        synchronized (bucket) {
            refill(bucket, maxRequests, windowMillis);
            if (bucket.availableTokens >= 1) {
                bucket.availableTokens -= 1;
                return true;
            }
            return false;
        }
    }

    private void refill(Bucket bucket, int maxRequests, long windowMillis) {
        long now = System.currentTimeMillis();
        long elapsed = now - bucket.lastRefillTimestamp;
        if (elapsed <= 0) {
            return;
        }
        double refillPerMillis = maxRequests / (double) windowMillis;
        bucket.availableTokens = Math.min(maxRequests, bucket.availableTokens + elapsed * refillPerMillis);
        bucket.lastRefillTimestamp = now;
    }
}
