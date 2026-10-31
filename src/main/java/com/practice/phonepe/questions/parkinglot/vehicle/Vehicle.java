package com.practice.phonepe.questions.parkinglot.vehicle;

public class Vehicle {

    private final VehicleSize size;
    private final String licenceNumber;

    public Vehicle(VehicleSize size, String licenceNumber) {
        this.size = size;
        this.licenceNumber = licenceNumber;
    }

    public VehicleSize getSize() {
        return size;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }
}
