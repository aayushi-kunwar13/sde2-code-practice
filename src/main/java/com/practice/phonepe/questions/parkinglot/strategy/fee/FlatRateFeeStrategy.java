package com.practice.phonepe.questions.parkinglot.strategy.fee;

import com.practice.phonepe.questions.parkinglot.entities.ParkingTicket;

public class FlatRateFeeStrategy implements FeeStrategy {

    private static final double rate_per_hour = 10.0;
    @Override
    public double calculateFee(ParkingTicket parkingTicket) {

        long duration = parkingTicket.getExistTimeStamp() - parkingTicket.getEntryTimeStamp();
        long hour = (duration / (1000*60*60)) + 1;
        return hour*rate_per_hour;

    }
}
