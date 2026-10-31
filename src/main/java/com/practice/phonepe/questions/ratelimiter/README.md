# Rate Limiter

**Design a rate limiter for an API.**

- Limit each client to N requests per time window, with a default limit and per-client overrides.
- Support several algorithms: fixed window, sliding window counter, sliding window log and token bucket.
- Rejected requests raise `RateLimitExceededException`.
- Several servers must share one limit (simulated with a shared store).

**Design:** Strategy for the algorithm, a `RateLimitStore` interface (in-memory now, Redis later) so state is shared,
config per client. Run `Demo` to see all four algorithms and the two-server case.
