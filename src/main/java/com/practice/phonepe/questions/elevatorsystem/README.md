# Elevator System

**Design a controller for N elevators in a building.**

- External requests: a person on floor F presses UP or DOWN.
- Internal requests: a person inside elevator E presses a floor.
- Dispatch each external request to the best elevator (nearest here, and the rule is swappable).
- Each elevator serves stops in its current direction before turning around.
- Displays show each elevator's floor and direction as it moves.

**Design:** State pattern (Idle / MovingUp / MovingDown), Strategy for elevator selection, Observer for displays,
a singleton `ElevatorSystem`, one thread per elevator. Run `ElevatorSystemDemo`.
