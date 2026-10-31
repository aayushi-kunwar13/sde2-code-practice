# Merchant Payout Scheduler

**Topic:** Sorted map / Grouping · **Difficulty:** Easy

Transactions are `transactionId,amountInCents,day`. Payouts happen every `payoutInterval` days, and a transaction made on
`day` is paid out on the **next** payout day: `(day / interval + 1) × interval`.
Skip malformed lines.

Return one line per payout day, in order:
```
Day 7: $12.50 [tx1, tx2]
```

**Approach:** a `TreeMap<payoutDay, List<transaction>>` keeps the days sorted. Sum the cents and format as `$X.YY`.
