# Parking Lot

**Design a multi-floor parking lot.**

- Floors have spots of different sizes. Vehicles have a size and a licence number.
- Park: find a suitable free spot and issue a ticket. Unpark: free the spot and charge a fee for the time parked.
- The spot-finding rule and the fee rule must be swappable.

**Design:** singleton `ParkingLot`, Strategy for spot selection (`BestFitStrategy`) and fees (`FlatRateFeeStrategy`),
active tickets in a `ConcurrentHashMap`.
