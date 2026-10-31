package com.practice.phonepe.questions.ratelimiter;

public interface RateLimiter {
    /** Throws RateLimitExceededException if clientId is over its configured limit. */
    void allowRequest(String clientId) throws RateLimitExceededException;
}
