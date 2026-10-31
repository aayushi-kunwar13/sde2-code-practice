package com.practice.phonepe.questions.parkinglot.entities;

import java.util.ArrayList;
import java.util.List;

public class ParkingFloor {
    private final int floorNumber;
    private List<ParkingSpot> spot;

    public ParkingFloor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.spot = new ArrayList<>();
    }

    public void addSpot(ParkingSpot parkingSpot) {
        spot.add(parkingSpot);
    }

    public List<ParkingSpot> getSpots() {
        return spot;
    }

}
