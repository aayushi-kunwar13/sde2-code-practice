package com.practice.phonepe.questions.ratelimiter;

/**
 * Wherever the request counters actually live. A single JVM's in-memory map only protects
 * that one process; multiple servers need to share one store so a client can't reset their
 * quota just by hitting a different instance. Both methods here map directly onto atomic
 * Redis commands (INCR, GET) -- swapping InMemoryRateLimitStore for a Redis-backed
 * implementation is the entire change needed to make this distributed.
 */
public interface RateLimitStore {

    /** Atomically increments the counter for key and returns the new value. */
    long incrementAndGet(String key);

    /** Reads the current counter for key, or 0 if it doesn't exist yet. */
    long get(String key);
}
