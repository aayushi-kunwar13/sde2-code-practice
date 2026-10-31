# Accept-Language Parser

**Topic:** String parsing · **Difficulty:** Easy → Medium

An HTTP `Accept-Language` header lists the languages a client wants, e.g. `"en-US, fr-CA, fr-FR"`.
Given the header and the languages the server supports, return the supported ones in the client's order of preference.

## Part 1: exact matches in header order
Trim spaces, match case-insensitively, keep header order, no duplicates.

```
header    = "   en-US   ,     fr-CA,     fr-FR     "
supported = ["fr-CA", "en-UK", "fr-FR", "en-US"]
→ [en-us, fr-ca, fr-fr]
```

## Part 2: quality weights
Entries can carry a weight: `"fr-FR;q=0.8, en-US;q=1"` (no `q` means 1.0). Sort by weight, highest first.
Drop `q=0` and treat an unparsable `q` as 0.

**Approach:** split on `,` then `;`, parse `q=`, filter to supported languages, stable-sort by weight descending.
