package com.practice.phonepe.questions.ratelimiter;

import com.practice.phonepe.questions.ratelimiter.strategy.RateLimitAlgorithm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class SimpleRateLimiter implements RateLimiter {

    private final RateLimitStore store;
    private final RateLimitAlgorithm algorithm;
    private final RateLimitConfig defaultConfig;
    private final Map<String, RateLimitConfig> clientConfigs = new ConcurrentHashMap<>();

    public SimpleRateLimiter(RateLimitStore store, RateLimitAlgorithm algorithm, RateLimitConfig defaultConfig) {
        this.store = store;
        this.algorithm = algorithm;
        this.defaultConfig = defaultConfig;
    }

    /** Different entities can have a different count-per-window; unconfigured ones get the default. */
    public void configureClient(String clientId, RateLimitConfig config) {
        clientConfigs.put(clientId, config);
    }

    @Override
    public void allowRequest(String clientId) {
        RateLimitConfig config = clientConfigs.getOrDefault(clientId, defaultConfig);

        boolean allowed;
        try {
            allowed = algorithm.allowRequest(store, clientId, config.getMaxRequests(), config.getWindowMillis());
        } catch (RuntimeException storeFailure) {
            // High availability: the rate limiter exists to protect the servers behind it --
            // if its own store is unreachable, blocking every request would be a worse outage
            // than the one it's guarding against. Fail open and let the request through.
            System.err.println("Rate limit store unavailable for '" + clientId + "', failing open: "
                    + storeFailure.getMessage());
            return;
        }

        if (!allowed) {
            throw new RateLimitExceededException(
                    "Rate limit exceeded for '" + clientId + "': max " + config.getMaxRequests()
                            + " requests per " + config.getWindowMillis() + "ms",
                    config.getWindowMillis());
        }
    }
}
