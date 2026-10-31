# Hackathon Platform (machine-coding round)

**Design a coding-contest platform.**

1. Add problems to a question library: description, tags, difficulty, score.
2. Contestants register with a name and department.
3. Fetch problems filtered by difficulty or tag and sorted by score, extensible to new filters and sorts.
4. A contestant solves a problem (with the time taken); list the problems a contestant has solved.
5. For a problem: how many users solved it and the average time taken.
6. Scoring is pluggable: a fixed score, or a score adjusted by solve time.
7. Return the current leader.
8. Top N most-solved problems for a tag.
9. **Extension:** after a solve, recommend 5 problems with similar tags.

**Design:** repositories + services, Strategy for scoring and recommendations, `Predicate` / `Comparator` for filters,
custom exceptions, unit tests in `src/test`. Run `HackathonPlatformDemo`; it prints each requirement in order.
