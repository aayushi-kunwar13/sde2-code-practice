package com.practice.phonepe.questions.ratelimiter.strategy;

import com.practice.phonepe.questions.ratelimiter.RateLimitStore;

public interface RateLimitAlgorithm {
    /** true if this request is allowed under maxRequests-per-windowMillis for clientId. */
    boolean allowRequest(RateLimitStore store, String clientId, int maxRequests, long windowMillis);
}
