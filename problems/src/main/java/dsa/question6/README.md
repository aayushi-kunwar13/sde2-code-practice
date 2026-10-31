# Spotting an Error Spike

**Topic:** Sliding window / Hashing · **Difficulty:** Medium → Hard

Log lines arrive in time order as `timestamp,merchantId,statusCode,count`.
Alert when a merchant's errors spike. Output alerts sorted by timestamp, then merchant, then status code.

## Part 1: threshold alert
For each `(merchant, non-200 status)`, if the errors in the last **30 seconds** add up to at least **5**,
emit one `TRIGGER` (only the first time).

## Part 2: compare against successes
Also require the failures to be significant next to successful traffic: alert only if
`failures ≥ 5` **and** `failures × 100 > successes (200s)` in the same window.
Every new log line re-checks every failure code that merchant has seen.

## Part 3: trigger and resolve
An alert can now clear. Emit `TRIGGER` when the condition becomes true and `RESOLVE` when it stops being true,
so the same alert can fire again later.

**Approach:** per key, a time-ordered list of `(timestamp, count)`; sum backwards while inside the 30s window.
Part 3 keeps a set of active alerts to detect state changes.
