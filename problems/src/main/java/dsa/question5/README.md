# Flight Cost Calculation

**Topic:** Graphs · **Difficulty:** Easy → Medium

Flights come as `SRC:DST:AIRLINE:COST` separated by commas, e.g. `"UK:US:FedEx:4,UK:FR:Jet1:2,US:UK:RyanAir:8"`.
Skip malformed entries (fewer than 4 fields, or a cost that isn't a number). Match places case-insensitively.

## Part 1: cheapest direct flight
Return the lowest cost of a direct `source → destination` flight, or `-1`.

## Part 2: at most one stop
If there's no direct flight, allow one connection (`UK → FR → US`) and return the cheapest total.

## Part 3: any number of stops, with the route
Return the route, the airlines used and the total cost:
`UK->FR->DE->US`, `Jet1->Lufthansa->Delta`, `10`.

## Part 4: cheapest route over any number of stops (not started)

**Approach:** Part 2 uses a map of `src-dst → cheapest cost` and tries every middle stop. Part 3 is a DFS with backtracking
that records the path and airlines. Part 4 would be Dijkstra.
