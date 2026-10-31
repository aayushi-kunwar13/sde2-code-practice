# Volume (Tiered) Pricing

**Topic:** Greedy / Math · **Difficulty:** Easy → Medium

Price tiers are `[upTo, unitPrice]`, ordered by `upTo`; `-1` means "and above".
Example: `[[10, 50], [20, 40], [-1, 30]]` means units 1–10 cost 50, 11–20 cost 40, and 21+ cost 30.

## Part 1: all-units pricing
Find the tier the quantity falls into. **Every** unit is charged that tier's price.

## Part 2: graduated pricing
Each unit is charged by the tier it falls in (like tax brackets): `15 units = 10 × 50 + 5 × 40 = 700`.

## Part 3: maximum quantity for a budget
Given a budget, return the most units you can buy under graduated pricing (`budget = 745 → 16`).

**Approach:** walk the tiers in order. Part 2 takes `min(quantity, upTo) - alreadyCounted` units per tier.
Part 3 spends the budget tier by tier until it can't fill the next tier, then divides what is left by that tier's price.
