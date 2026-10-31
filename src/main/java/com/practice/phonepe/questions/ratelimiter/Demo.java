package com.practice.phonepe.questions.ratelimiter;

import com.practice.phonepe.questions.ratelimiter.strategy.FixedWindowCounterStrategy;
import com.practice.phonepe.questions.ratelimiter.strategy.SlidingWindowCounterStrategy;
import com.practice.phonepe.questions.ratelimiter.strategy.SlidingWindowLogStrategy;
import com.practice.phonepe.questions.ratelimiter.strategy.TokenBucketStrategy;

public final class Demo {

    public static void main(String[] args) {
        RateLimitStore store = new InMemoryRateLimitStore();

        System.out.println("-- Fixed window: client-A limited to 2 requests/sec --");
        SimpleRateLimiter fixedWindowLimiter =
                new SimpleRateLimiter(store, new FixedWindowCounterStrategy(), new RateLimitConfig(5, 1000));
        fixedWindowLimiter.configureClient("client-A", new RateLimitConfig(2, 1000));
        for (int i = 0; i < 4; i++) {
            attempt(fixedWindowLimiter, "client-A", i);
        }

        System.out.println("-- Sliding window: client-B limited to 3 requests/sec, different algorithm --");
        SimpleRateLimiter slidingWindowLimiter =
                new SimpleRateLimiter(store, new SlidingWindowCounterStrategy(), new RateLimitConfig(3, 1000));
        for (int i = 0; i < 5; i++) {
            attempt(slidingWindowLimiter, "client-B", i);
        }

        System.out.println("-- Sliding window log: client-E limited to 3 requests per 500ms, exact eviction --");
        SimpleRateLimiter slidingLogLimiter =
                new SimpleRateLimiter(store, new SlidingWindowLogStrategy(), new RateLimitConfig(3, 500));
        for (int i = 0; i < 4; i++) {
            attempt(slidingLogLimiter, "client-E", i); // burst: first 3 allowed, 4th denied
        }
        System.out.println("   waiting 520ms so all 3 timestamps age out of the 500ms window...");
        sleep(520);
        attempt(slidingLogLimiter, "client-E", 4); // window is empty again -> allowed

        System.out.println("-- Token bucket: client-D allows a burst of 3, then refills gradually --");
        SimpleRateLimiter tokenBucketLimiter =
                new SimpleRateLimiter(store, new TokenBucketStrategy(), new RateLimitConfig(3, 1000));
        for (int i = 0; i < 4; i++) {
            attempt(tokenBucketLimiter, "client-D", i); // burst: first 3 allowed, 4th denied
        }
        System.out.println("   waiting 350ms for ~1 token to refill (3 tokens / 1000ms)...");
        sleep(350);
        attempt(tokenBucketLimiter, "client-D", 4); // should be allowed again

        System.out.println("-- Distribution: two limiter instances (simulating two servers) sharing one store --");
        System.out.println("   client-C limited to 3 requests/sec, total across BOTH servers");
        RateLimitConfig sharedLimit = new RateLimitConfig(3, 1000);
        SimpleRateLimiter serverA = new SimpleRateLimiter(store, new FixedWindowCounterStrategy(), sharedLimit);
        SimpleRateLimiter serverB = new SimpleRateLimiter(store, new FixedWindowCounterStrategy(), sharedLimit);
        for (int i = 0; i < 4; i++) {
            boolean useServerA = i % 2 == 0;
            SimpleRateLimiter server = useServerA ? serverA : serverB;
            String serverName = useServerA ? "server-A" : "server-B";
            try {
                server.allowRequest("client-C");
                System.out.println("request " + i + " via " + serverName + " -> ALLOWED");
            } catch (RateLimitExceededException e) {
                System.out.println("request " + i + " via " + serverName + " -> DENIED: " + e.getMessage());
            }
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void attempt(RateLimiter limiter, String clientId, int requestNumber) {
        try {
            limiter.allowRequest(clientId);
            System.out.println("request " + requestNumber + " -> ALLOWED");
        } catch (RateLimitExceededException e) {
            System.out.println("request " + requestNumber + " -> DENIED: " + e.getMessage());
        }
    }
}
