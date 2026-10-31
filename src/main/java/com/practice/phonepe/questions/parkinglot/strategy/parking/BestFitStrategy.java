package com.practice.phonepe.questions.parkinglot.strategy.parking;

import com.practice.phonepe.questions.parkinglot.entities.ParkingFloor;
import com.practice.phonepe.questions.parkinglot.entities.ParkingSpot;
import com.practice.phonepe.questions.parkinglot.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BestFitStrategy implements ParkingStrategy {

    @Override
    public Optional<ParkingSpot> findSpot(List<ParkingFloor> floors, Vehicle vehicle) {
        List<ParkingSpot> candidates = new ArrayList<>();

        for (ParkingFloor floor : floors) {
            for (ParkingSpot spot : floor.getSpots()) {
                if (spot.canFitVehicle(vehicle)) {
                    candidates.add(spot);
                }
            }
        }

        candidates.sort(Comparator.comparingInt(spot -> spot.getSpotSize().ordinal()));

        for (ParkingSpot spot : candidates) {
            if (spot.tryPark(vehicle)) {
                return Optional.of(spot);
            }
        }

        return Optional.empty();
    }
}
