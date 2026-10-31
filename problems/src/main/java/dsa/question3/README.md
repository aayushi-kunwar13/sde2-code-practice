# Idempotent Request Replay

**Topic:** Hashing / Caching · **Difficulty:** Medium

Requests arrive in order, each with an optional **idempotency key** and a value (the response it would produce).
Return the response sent for every request.

## Part 1: replay by key
- No key → process it and return its own value.
- First time a key is seen → process it and remember the response.
- Key seen before → return the **stored** response (a replay), not the new value.

## Part 2: keys expire
Each request also has a timestamp, and stored keys expire after `ttl`.
A request after `storedTime + ttl` is processed fresh and replaces the stored response.

## Part 3: conflicting retries
Within the TTL, a request with the same key but a **different** value is a client error: return `-1`.
The same value is a normal retry.

**Approach:** `HashMap<key, (response, expiresAt)>`, checked on every request.
