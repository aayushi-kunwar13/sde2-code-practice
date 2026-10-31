# Code Practice

My Java practice for SDE 2 interviews: multi-part DSA questions, LeetCode-style problems and low-level design (machine coding).
Every question has its statement next to the code: a `README.md` in its folder, or a header comment at the top of the file.

Also shown on my prep journal site, under Code Practice.

## DSA: multi-part questions (`problems/src/main/java/dsa/`)

| # | Question | Topic | Parts |
|---|---|---|---|
| 1 | [Currency Conversion](problems/src/main/java/dsa/question1) | Graphs (DFS / BFS) | 3 |
| 2 | [Accept-Language Parser](problems/src/main/java/dsa/question2) | String parsing | 2 |
| 3 | [Idempotent Request Replay](problems/src/main/java/dsa/question3) | Hashing / Caching | 3 |
| 4 | [Volume (Tiered) Pricing](problems/src/main/java/dsa/question4) | Greedy / Math | 3 |
| 5 | [Flight Cost Calculation](problems/src/main/java/dsa/question5) | Graphs | 3 (+1 in progress) |
| 6 | [Spotting an Error Spike](problems/src/main/java/dsa/question6) | Sliding window | 3 |
| 7 | [Payment ↔ Invoice Matching](problems/src/main/java/dsa/question7) | String parsing / Sorting | 3 |
| 8 | [Task / Sub-task Hierarchy](problems/src/main/java/dsa/question8) | Hashing / Sorting | 1 |
| 9 | [Merchant Payout Scheduler](problems/src/main/java/dsa/question9) | Sorted map | 1 |

## DSA: single problems

| Problem | Difficulty | Topic |
|---|---|---|
| [Bulls and Cows](problems/src/main/java/dsa/BullAndCows.java) | Medium | Hashing |
| [Rank Teams by Votes](problems/src/main/java/dsa/RankTeamByVotes.java) | Medium | Sorting |
| [Token Bucket Rate Limiter](problems/src/main/java/dsa/TokenBucket.java) | Medium | Rate limiting |
| [Payment Parser](problems/src/main/java/dsa/PaymentPArser.java) | Easy | String parsing |
| [Text Justification](problems/src/main/java/dsa/TextJustification.java) (in progress) | Hard | Strings |
| [Snake and Ladder](problems/src/main/java/dsa/SnakeAndLadderProblem.java) (notes) | Medium | Simulation |

## Low-level design (`src/main/java/com/practice/phonepe/`)

| Design | Patterns |
|---|---|
| [Hackathon Platform](src/main/java/com/practice/phonepe/interview) (machine-coding round, with tests) | Strategy, Repository, Service layer |
| [BookMyShow](src/main/java/com/practice/phonepe/questions/bookmyshow) | Seat locking, Strategy, Factory |
| [BookMyShow v2](src/main/java/com/practice/phonepe/questions/bookmyshowv2) (in progress) | Entities |
| [Elevator System](src/main/java/com/practice/phonepe/questions/elevatorsystem) | State, Strategy, Observer |
| [File System](src/main/java/com/practice/phonepe/questions/filesystem) | Composite |
| [Notification System](src/main/java/com/practice/phonepe/questions/notificationsystem) | Builder, Factory, Decorator, Observer |
| [Notification System v2](src/main/java/com/practice/phonepe/questions/notificationsystemv2) | Factory, Decorator, Observer |
| [Parking Lot](src/main/java/com/practice/phonepe/questions/parkinglot) | Singleton, Strategy |
| [Rate Limiter](src/main/java/com/practice/phonepe/questions/ratelimiter) | Strategy (4 algorithms), shared store |
| [Vending Machine](src/main/java/com/practice/phonepe/questions/vendingmachine) | State |
| [URL Shortener](problems/src/main/java/lld/urlShortner) | Base-62 encoding, concurrency |
| [Rate Limiter (practice)](problems/src/main/java/lld/ratelimiter) | Fixed / sliding window, token bucket |
| [Snake and Ladder](problems/src/main/java/lld/snakeandladder) (in progress) | Entities |

## Run

Java 16, Gradle wrapper included.

```bash
./gradlew compileJava
java -cp build/classes/java/main com.practice.phonepe.interview.HackathonPlatformDemo
./gradlew test --tests "com.practice.phonepe.interview.*"
```
