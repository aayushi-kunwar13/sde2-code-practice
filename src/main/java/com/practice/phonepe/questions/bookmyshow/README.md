# BookMyShow (movie ticket booking)

**Design the backend for booking movie tickets.**

- Admins add movies, theatres and screens (seat rows with a type: normal, premium, recliner) and create shows.
- Users search shows for a movie in a city and see seat availability.
- A user picks seats. The seats are **locked for a short time** (30s here) while they pay, and other users can't take them.
- Pay by card or UPI. On success the booking is confirmed; if the lock expires, the seats are released.
- Price depends on seat type.
- **Concurrency:** many users racing for the same seat; exactly one may win.

**Design:** repositories + services, `SeatLockManager` (per-seat locks with expiry), Strategy for pricing and payment,
a factory for payment methods, and custom exceptions. Run `BookMyShowDemo` (includes a 10-thread race for one seat).
