package com.practice.phonepe.questions.parkinglot;


import com.practice.phonepe.questions.parkinglot.entities.ParkingFloor;
import com.practice.phonepe.questions.parkinglot.entities.ParkingSpot;
import com.practice.phonepe.questions.parkinglot.entities.ParkingTicket;
import com.practice.phonepe.questions.parkinglot.strategy.fee.FeeStrategy;
import com.practice.phonepe.questions.parkinglot.strategy.fee.FlatRateFeeStrategy;
import com.practice.phonepe.questions.parkinglot.strategy.parking.BestFitStrategy;
import com.practice.phonepe.questions.parkinglot.strategy.parking.ParkingStrategy;
import com.practice.phonepe.questions.parkinglot.vehicle.Vehicle;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingLot {
    private static ParkingLot instance;
    private final List<ParkingFloor> floors = new ArrayList<>();
    private final Map<String, ParkingTicket> activeTickets;
    private FeeStrategy feeStrategy;
    private ParkingStrategy parkingStrategy;

    private ParkingLot() {
        this.feeStrategy = new FlatRateFeeStrategy();
        this.parkingStrategy = new BestFitStrategy();
        this.activeTickets = new ConcurrentHashMap<>();
    }

    public static synchronized ParkingLot getInstance() {
        if (instance == null) {
            instance = new ParkingLot();
        }
        return instance;
    }

    public void addFloor(ParkingFloor floor) {
        floors.add(floor);
    }

    public void setFeeStrategy (FeeStrategy feeStrategy) {
        this.feeStrategy = feeStrategy;
    }

    public void setParkingStrategy(ParkingStrategy parkingStrategy) {
        this.parkingStrategy = parkingStrategy;
    }

    public Optional<ParkingTicket> parkVehicle(Vehicle vehicle) {
        Optional<ParkingSpot> availableSpot = parkingStrategy.findSpot(floors, vehicle);

        if (availableSpot.isPresent()) {
            ParkingSpot spot = availableSpot.get();
            ParkingTicket ticket = new ParkingTicket(spot, vehicle);
            activeTickets.put(vehicle.getLicenceNumber(), ticket);
            System.out.printf("%s parked at %s. Ticket: %s\n", vehicle.getLicenceNumber(), spot.getSpotId(), ticket.getTicketId());
            return Optional.of(ticket);
        }

        System.out.println("No available spot for " + vehicle.getLicenceNumber());
        return Optional.empty();
    }

    public Optional<Double> unparkVehicle(String licenseNumber) {
        ParkingTicket ticket = activeTickets.remove(licenseNumber);
        if (ticket == null) {
            System.out.println("Ticket not found");
            return Optional.empty();
        }

        ticket.setExistTimeStamp();
        ticket.getParkingSpot().unparkVehicle();

        Double parkingFee = feeStrategy.calculateFee(ticket);

        return Optional.of(parkingFee);
    }
}