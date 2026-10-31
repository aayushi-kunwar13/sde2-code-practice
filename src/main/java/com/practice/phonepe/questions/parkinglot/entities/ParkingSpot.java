package com.practice.phonepe.questions.parkinglot.entities;

import com.practice.phonepe.questions.parkinglot.vehicle.Vehicle;
import com.practice.phonepe.questions.parkinglot.vehicle.VehicleSize;

import static com.practice.phonepe.questions.parkinglot.vehicle.VehicleSize.*;

public class ParkingSpot {
    private final int spotId;
    private Boolean isOccupied;
    private Vehicle parkedVehicle;
    private final VehicleSize spotSize;

    public ParkingSpot(int spotId, VehicleSize spotSize) {
        this.spotId = spotId;
        this.spotSize = spotSize;
        this.isOccupied = false;
        this.parkedVehicle = null;
    }

    public int getSpotId() {
        return spotId;
    }

    public Boolean getOccupied() {
        return isOccupied;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    public void setParkedVehicle(Vehicle parkedVehicle) {
        this.parkedVehicle = parkedVehicle;
    }

    public VehicleSize getSpotSize() {
        return spotSize;
    }

    public synchronized Boolean isAvailable() {
        return !isOccupied;
    }

    public synchronized void parkVehicle(Vehicle vehicle) {
        this.parkedVehicle = vehicle;
        this.isOccupied = true;
    }

    public synchronized void unparkVehicle() {
        this.parkedVehicle = null;
        this.isOccupied = false;
    }

    public Boolean canFitVehicle(Vehicle vehicle) {
        if(isOccupied) {
            return false;
        }
        switch (vehicle.getSize()) {
            case SMALL:
                return SMALL.equals(getSpotSize());
            case MEDIUM:
                return MEDIUM.equals(getSpotSize()) || LARGE.equals(getSpotSize());
            case LARGE:
                return LARGE.equals(getSpotSize());

            default:
                return false;
        }
    }

    public synchronized boolean tryPark(Vehicle vehicle) {
        if (isOccupied || !canFitVehicle(vehicle)) {
            return false;
        }
        parkVehicle(vehicle);
        return true;
    }

}
