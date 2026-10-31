/**
 * Problem: Token Bucket Rate Limiter
 * Difficulty: Medium
 * Topic: Rate limiting / Concurrency
 * Question: Implement allowRequest() for a rate limiter: a bucket holds up to capacity tokens and refills at refillPerSec; each request spends one token or is rejected.
 * Insight: Refill lazily on each call from the elapsed time (capped at capacity) instead of using a background timer; synchronized keeps it thread-safe.
 */
package dsa;

public class TokenBucket {

        private final int capacity;
        private final double refillPerSec;
        private double tokens;
        private long lastRefill;

        public TokenBucket(int capacity, double refillPerSec) {
            this.capacity = capacity;
            this.refillPerSec = refillPerSec;
            this.tokens = capacity;                  // start full
            this.lastRefill = System.nanoTime();
        }

        public synchronized boolean allowRequest() {
            long now = System.nanoTime();
            double elapsedSec = (now - lastRefill) / 1e9;
            tokens = Math.min(capacity, tokens + elapsedSec * refillPerSec);   // refill, capped
            lastRefill = now;

            if (tokens >= 1) {
                tokens--;                            // spend one token
                return true;
            }
            return false;
        }

}
