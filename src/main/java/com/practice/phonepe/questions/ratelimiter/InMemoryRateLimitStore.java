package com.practice.phonepe.questions.ratelimiter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Single-process implementation -- only correct if there's exactly one server. Every caller
 * uses window-bucketed keys (e.g. "clientId:windowStartMillis"), so a key is never reused
 * once its window has passed; that's why there's no explicit expiry here. A real deployment
 * would use Redis instead, where INCR + EXPIRE are both atomic single commands and every
 * server pointed at the same Redis instance shares these same counts.
 */
public final class InMemoryRateLimitStore implements RateLimitStore {

    private final ConcurrentHashMap<String, AtomicLong> counters = new ConcurrentHashMap<>();

    @Override
    public long incrementAndGet(String key) {
        return counters.computeIfAbsent(key, k -> new AtomicLong(0)).incrementAndGet();
    }

    @Override
    public long get(String key) {
        AtomicLong counter = counters.get(key);
        return counter == null ? 0 : counter.get();
    }
}
