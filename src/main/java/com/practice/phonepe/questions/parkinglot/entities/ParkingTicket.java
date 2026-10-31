package com.practice.phonepe.questions.parkinglot.entities;

import com.practice.phonepe.questions.parkinglot.vehicle.Vehicle;

import java.util.Date;
import java.util.UUID;

public class ParkingTicket {
    private final String ticketId;
    private final ParkingSpot parkingSpot;
    private final Vehicle vehicle;
    private final long entryTimeStamp;
    private long existTimeStamp;

    public ParkingTicket(ParkingSpot parkingSpot, Vehicle vehicle) {
        this.ticketId = UUID.randomUUID().toString();
        this.parkingSpot = parkingSpot;
        this.vehicle = vehicle;
        this.entryTimeStamp = new Date().getTime();
    }

    public String getTicketId() {
        return ticketId;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public long getEntryTimeStamp() {
        return entryTimeStamp;
    }

    public long getExistTimeStamp() {
        return existTimeStamp;
    }

    public void setExistTimeStamp() {
        this.existTimeStamp = new Date().getTime();
    }
}
