# Currency Conversion

**Topic:** Graphs (DFS / BFS) · **Difficulty:** Medium

You get exchange rates as one string, `FROM:TO:RATE` pairs separated by commas, e.g.
`"USD:CAD:1.3,EUR:USD:1.1,GBP:EUR:1.15"`. A rate also works in reverse (`TO → FROM` is `1 / RATE`).

## Part 1: direct conversion
Convert `amount` from one currency to another using a **direct** rate (either direction). Round to 2 decimals.
Trim stray spaces (`"USD :CAD:1.3 "`). If there is no direct rate, return `-1`.

## Part 2: convert through other currencies
If there is no direct rate, go through other currencies (`AUD → USD → GBP`).
Return the **route**, the combined **rate** and the **converted value**, or "No available conversion".

## Part 3: shortest route
Several routes may exist. Return the one with the **fewest hops**.

```
input = "USD:EUR:0.85,EUR:GBP:0.87,USD:CAD:1.3,CAD:AUD:1.1,AUD:NZD:1.05,NZD:GBP:0.5"
USD → GBP  ⇒  route USD -> EUR -> GBP
```

**Approach:** build a graph with both directions. Part 2: DFS that multiplies rates along the path and backtracks.
Part 3: BFS with a parent map, then walk back from the target to rebuild the route and rate.
