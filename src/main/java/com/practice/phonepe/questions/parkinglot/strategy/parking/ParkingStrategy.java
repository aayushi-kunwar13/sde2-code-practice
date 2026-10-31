package com.practice.phonepe.questions.parkinglot.strategy.parking;

import com.practice.phonepe.questions.parkinglot.entities.ParkingFloor;
import com.practice.phonepe.questions.parkinglot.entities.ParkingSpot;
import com.practice.phonepe.questions.parkinglot.vehicle.Vehicle;

import java.util.List;
import java.util.Optional;

public interface ParkingStrategy {
    Optional<ParkingSpot> findSpot(List<ParkingFloor> floors, Vehicle vehicle);
}
