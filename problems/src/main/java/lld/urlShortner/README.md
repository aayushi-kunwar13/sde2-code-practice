# URL Shortener

**Design a URL shortener.**

- `getShortURL(longUrl)` returns `http://short.ly/<code>`. The same long URL always gets the same code.
- `expandShortUrl(shortUrl)` returns the original URL.
- Must be safe under concurrent calls.

**Design:** an `AtomicLong` counter encoded in **base 62** (0-9, a-z, A-Z), with two `ConcurrentHashMap`s (code ↔ URL).
`computeIfAbsent` makes "same URL → same code" atomic.
